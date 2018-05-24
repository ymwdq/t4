package com.alibaba.dubbo.performance.demo.agent.message.model;

/**
 * 定义消息队列
 */
public interface MessageQueue {
    public void offer(Message msg);
    public Message poll();
    public int size();
}
