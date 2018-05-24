package com.alibaba.dubbo.performance.demo.agent.message;

import com.alibaba.dubbo.performance.demo.agent.message.model.Message;

/**
 * 用于 agent 之间传送消息
 */
public class MessageImpl implements Message {
    private int id;
    private Object body;
    private byte[] binaryBody;

    public MessageImpl(int id, String body) {
        this.id = id;
        this.body = body;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Object getBody() {
        return body;
    }


    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            return this.id == (((Message)obj).getId()) && this.body.equals(((Message)obj).getBody());
        }
        return false;
    }

    @Override
    public byte[] getBinaryBody() {
        return binaryBody;
    }

    @Override
    public void setBinaryBody(byte[] body) {
        this.binaryBody = body;
    }
}
