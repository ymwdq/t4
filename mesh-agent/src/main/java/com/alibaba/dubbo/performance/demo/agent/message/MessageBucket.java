package com.alibaba.dubbo.performance.demo.agent.message;

import com.alibaba.dubbo.performance.demo.agent.message.model.IMessageBucket;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;

import java.util.ArrayList;

/**
 * 使得消息聚集，批量发送，不考虑线程安全问题，推荐使用 threadlocal 保存，使用 pool 动态获取。
 */
public class MessageBucket implements IMessageBucket {
    private static int DEFAULT_CAPACITY = 2000;
    private ArrayList<Message> messages = new ArrayList<>(DEFAULT_CAPACITY);
    private volatile int currentSize = 0;
    private final int CAPACITY;

    public MessageBucket(int capacity) {
        this.CAPACITY = capacity;
    }

    public MessageBucket() {
        this.CAPACITY = DEFAULT_CAPACITY;
    }

    @Override
    public boolean full() {
        return currentSize >= CAPACITY;
    }

    @Override
    public void addMessage(Message message) {
        messages.set(++currentSize, message);
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public void clear() {
        currentSize = 0;
    }
}
