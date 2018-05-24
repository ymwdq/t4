package com.alibaba.dubbo.performance.demo.agent.message;

import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.message.model.MessageQueue;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * not thread safe
 */
public class MessageQueueSafeImpl implements MessageQueue {
    private BlockingQueue<Message> queue = new ArrayBlockingQueue<>(5120);

    @Override
    public void offer(Message message) {
        queue.offer(message);
    }

    @Override
    public Message poll() {
        return queue.poll();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
