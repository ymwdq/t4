package com.alibaba.dubbo.performance.demo.agent.message.model;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by ym on 18-5-7.
 */
public interface IMessageBucket {
    boolean full();
    void addMessage(Message message);
    int size();
    void clear();
}
