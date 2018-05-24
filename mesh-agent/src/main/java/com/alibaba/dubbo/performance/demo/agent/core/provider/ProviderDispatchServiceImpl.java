package com.alibaba.dubbo.performance.demo.agent.core.provider;


import com.alibaba.dubbo.performance.demo.agent.core.consumer.model.ProviderAgentDispatchService;
import com.alibaba.dubbo.performance.demo.agent.core.loadbalance.LoadBalanceStrategy;
import com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper.HelloClientInitializer;
import com.alibaba.dubbo.performance.demo.agent.message.MessageBucketQueue;
import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProviderDispatchServiceImpl implements ProviderAgentDispatchService {

    private List<Endpoint> endpoints = null;
    private BlockingQueue<String> sendQueue;
    private final Object lock = new Object();
    private Channel channel;
    private List<String> resultList = new ArrayList<>();

    ProviderDispatchServiceImpl(IRegistry registry) {
        if (null == endpoints) {
            synchronized (lock) {
                if (null == endpoints) {
                    try {
                        EventLoopGroup group = new NioEventLoopGroup();
                        endpoints = registry.find("consumer");
                        // 获取consumer的agent
                        Endpoint endpoint = endpoints.get(0);
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(group)
                                .channel(NioSocketChannel.class)
                                .handler(new HelloClientInitializer());
                        channel = bootstrap.connect(endpoint.getHost(), endpoint.getPort()).sync().channel();
                        System.out.println("***********初始化channel完成************");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void setSendQueue(BlockingQueue<String> sendQueue) {
        this.sendQueue = sendQueue;
    }

    @Override
    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void setLoadbalanceStrategy(LoadBalanceStrategy strategy) {

    }

    @Override
    public void setBucketQueue(MessageBucketQueue messageBucketQueue) {

    }

    @Override
    public void start() {
//        for (int i = 0; i < 8; i++) {
            handleSendQueue();
//        }

    }

    private void handleSendQueue() {
        //新建一个线程负责发送处理完的结果
        new Thread(() -> {
            while (true) {
                try {
                    resultList.clear();
                    //一次取出发送队列中所有元素
                    int numCount = sendQueue.drainTo(resultList);
//                    String sendMsg = sendQueue.take();
//                    System.out.println("回复消息:"+sendMsg);
                    // to do
                    if (numCount > 0) {
                        for (String sendMsg : resultList) {
                            channel.writeAndFlush(sendMsg + "\n");
                        }
                    } else {
//                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
