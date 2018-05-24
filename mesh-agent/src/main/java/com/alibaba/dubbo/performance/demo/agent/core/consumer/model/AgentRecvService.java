package com.alibaba.dubbo.performance.demo.agent.core.consumer.model;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.Task;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 负责处理 consumer 接收队列，将相应的 http 请求解码并按 ID 分发给各个 handler
 */
public interface AgentRecvService {
    void start();

    void registerHandler(ChannelInboundHandlerAdapter handlerAdapter);

    void registerTask(Task task, Message message);
}
