package com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper;

import com.alibaba.dubbo.performance.demo.agent.core.provider.ProviderMessageQueueManager;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
    private static EtcdRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));
    private ProviderMessageQueueManager providerMessageQueueManager = new ProviderMessageQueueManager(registry);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        providerMessageQueueManager.offerRecvQueue(msg);
        ctx.flush();
//        ctx.writeAndFlush("OK");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        super.channelActive(ctx);
    }
}