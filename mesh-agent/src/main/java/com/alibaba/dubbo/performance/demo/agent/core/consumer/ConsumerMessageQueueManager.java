package com.alibaba.dubbo.performance.demo.agent.core.consumer;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.model.AgentDispatchService;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.Task;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConsumerMessageQueueManager {
    private volatile BlockingQueue<Message> sendQueue;
    private volatile BlockingQueue<String> recvQueue;
    private AgentRecvServiceImpl agentRecvService;
    private AgentDispatchService agentDispatchService;

    private static volatile ConsumerMessageQueueManager consumerMessageQueueManager;

    public static ConsumerMessageQueueManager getInstance() {
        return consumerMessageQueueManager;
    }

    public ConsumerMessageQueueManager(IRegistry registry) {
        recvQueue = new ArrayBlockingQueue<>(500);
        sendQueue = new ArrayBlockingQueue<>(500);
        agentRecvService = new AgentRecvServiceImpl();
        agentRecvService.setRecvQueue(recvQueue);
        agentRecvService.start();
        agentDispatchService = new AgentDispatchServiceImpl(registry);
        agentDispatchService.setSendQueue(sendQueue);
        agentDispatchService.start();
        consumerMessageQueueManager = this;
    }

    public BlockingQueue<Message> getSendQueue() {
        return sendQueue;
    }


    public void offerSendQueue(Message msg, Task task) {
        try {
            sendQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agentRecvService.registerTask(task, msg);
    }

    public void offerRecvQueue(String msg) {
        try {
            recvQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
