package com.alibaba.dubbo.performance.demo.agent.core.consumer;


import com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhelper.AgentClientInitializer;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.model.AgentDispatchService;
import com.alibaba.dubbo.performance.demo.agent.core.loadbalance.LoadBalanceStrategy;
import com.alibaba.dubbo.performance.demo.agent.message.MessageBucketQueue;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.dubbo.performance.demo.agent.message.util.MessageUtil;
import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class AgentDispatchServiceImpl implements AgentDispatchService {
    private static Logger logger = LoggerFactory.getLogger(AgentDispatchServiceImpl.class);

    private List<Endpoint> endpoints = null;
    private final Object lock = new Object();
    private Random random = new Random();
    private BlockingQueue<Message> sendQueue;
    private LoadBalanceStrategy loadBalanceStrategy;
    private List<Channel> channelList = new ArrayList<>();

    AgentDispatchServiceImpl(IRegistry registry) {

        if (null == endpoints) {
            synchronized (lock) {
                if (null == endpoints) {
                    try {
                        endpoints = registry.find("provider");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        for (Endpoint endpoint : endpoints) {
//            System.out.println("end points " + endpoint.getHost()+":"+endpoint.getPort());
            try {
                EventLoopGroup group = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new AgentClientInitializer());
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                channelList.add(bootstrap.connect(endpoint.getHost(), endpoint.getPort()).sync().channel());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }



    @Override
    public void setSendQueue(BlockingQueue<Message>  sendQueue) {
        this.sendQueue = sendQueue;
    }

    @Override
    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void setLoadbalanceStrategy(LoadBalanceStrategy strategy) {
        this.loadBalanceStrategy = strategy;
    }

    @Override
    public void setBucketQueue(MessageBucketQueue messageBucketQueue) {

    }


    private void sendByChannel(String msg) {
        // 简单的负载均衡，随机取一个
        Channel channel=channelList.get(random.nextInt(channelList.size()));
        channel.writeAndFlush(msg + "\n");
    }


    @Override
    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Message sendMsg = sendQueue.take();
                    // to do
                    logger.info("send queue size" + sendQueue.size());
                    sendByChannel(MessageUtil.msgToString(sendMsg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
