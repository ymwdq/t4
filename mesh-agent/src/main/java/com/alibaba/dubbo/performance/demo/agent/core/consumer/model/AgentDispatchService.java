package com.alibaba.dubbo.performance.demo.agent.core.consumer.model;

import com.alibaba.dubbo.performance.demo.agent.core.loadbalance.LoadBalanceStrategy;
import com.alibaba.dubbo.performance.demo.agent.message.MessageBucketQueue;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 该服务的作用是将 bucket queue 按照负载均衡算法分发到各个 provider-agent 上
 */
public interface AgentDispatchService {
    void start();
    void setEndpoints(List<Endpoint> endpoints);
    void setLoadbalanceStrategy(LoadBalanceStrategy strategy);
    void setBucketQueue(MessageBucketQueue messageBucketQueue);
    void setSendQueue(BlockingQueue<Message> sendQueue);
}
