package com.alibaba.dubbo.performance.demo.agent.core.consumer;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.model.AgentRecvService;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.IdGenerator;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.Task;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.message.util.MessageUtil;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;


public class AgentRecvServiceImpl implements AgentRecvService {
    private BlockingQueue<String> recvQueue;
    private TaskMessageMap map = new TaskMessageMap();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AgentRecvServiceImpl.class);

    void setRecvQueue(BlockingQueue<String> messageQueue) {
        this.recvQueue = messageQueue;
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (true) {
                Message msg = null;
                try {
                    msg = MessageUtil.stringToMsg(recvQueue.take());
                    logger.info("recvtime: " + System.currentTimeMillis() + " recvQueueSize: " + recvQueue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Task task = map.get(msg);
//                    logger.info("setResult: " + Id + " time: " + System.currentTimeMillis());
                task.setResult((String)msg.getBody());
                IdGenerator.freeId(msg.getId());
//                    logger.info("setResult: " + Id + " time: " + System.currentTimeMillis());
            }
        }).start();
    }

    @Override
    public void registerHandler(ChannelInboundHandlerAdapter handlerAdapter) {

    }

    @Override
    public void registerTask(Task task, Message msg) {
        map.put(task, msg);
    }

    /**
     * not thread safe
     */
    public class TaskMessageMap {
        private Task[] taskMap = new Task[3000];

        public synchronized void put(Task task, Message msg) {
            taskMap[msg.getId()] = task;
        }

        public synchronized Task get(Message msg) {
            Task r = taskMap[msg.getId()];
            clear(msg);
            return r;
        }

        public synchronized void clear(Message msg) {
            taskMap[msg.getId()] = null;
        }

    }
}