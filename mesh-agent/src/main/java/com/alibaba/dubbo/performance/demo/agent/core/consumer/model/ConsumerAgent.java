package com.alibaba.dubbo.performance.demo.agent.core.consumer.model;

import com.alibaba.dubbo.performance.demo.agent.core.loadbalance.Status;

/**
 * Created by ym on 18-5-7.
 */
public abstract class ConsumerAgent {
    private int servicePort;
    private int agentPort;
    public abstract Status getStatus();
    public abstract void register();
    public abstract void start();
}
