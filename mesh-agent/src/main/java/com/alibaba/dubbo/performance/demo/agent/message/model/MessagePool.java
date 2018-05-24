package com.alibaba.dubbo.performance.demo.agent.message.model;

/**
 * Created by ym on 18-5-9.
 */
public interface MessagePool {
    public void freeMsg(Message msg);
    public Message getMsg();
}
