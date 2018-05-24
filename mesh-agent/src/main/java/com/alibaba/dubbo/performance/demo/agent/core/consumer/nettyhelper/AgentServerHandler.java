package com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhelper;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.ConsumerMessageQueueManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class AgentServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(AgentServerHandler.class);
    private static ConsumerMessageQueueManager consumerMessageQueueManager = ConsumerMessageQueueManager.getInstance();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//        logger.info("consumer recv: " + msg);
        consumerMessageQueueManager.offerRecvQueue(msg);
        ctx.writeAndFlush("OK");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        super.channelActive(ctx);
    }
}