package com.alibaba.dubbo.performance.demo.agent.message.model;

import io.netty.buffer.ByteBuf;

/**
 * Created by ym on 18-5-6.
 */
public interface Message {
    int getId();
    Object getBody();
    byte[] getBinaryBody();
    void setId(int id);
    void setBody(Object body);
    void setBinaryBody(byte[] body);
}
