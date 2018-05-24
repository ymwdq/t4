package com.alibaba.dubbo.performance.demo.agent.core.provider;

import com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper.HelloClientInitializer;
import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;
import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;


public class ProviderRecvServiceImpl {
    private volatile BlockingQueue<String> recvQueue;
    private volatile BlockingQueue<String> sendQueue;
    private Logger logger = LoggerFactory.getLogger("ProviderRecvServiceImpl");

    private final Object lock = new Object();
    private Channel channel = null;

    ProviderRecvServiceImpl(IRegistry registry) {
        if (null == channel) {
            synchronized (lock) {
                if (null == channel) {
                    try {
                        EventLoopGroup group = new NioEventLoopGroup();
                        // 获取consumer的agent
                        Endpoint endpoint = registry.find("consumer").get(0);
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(group)
                                .channel(NioSocketChannel.class)
                                .handler(new HelloClientInitializer());
                        channel = bootstrap.connect(endpoint.getHost(), endpoint.getPort()).sync().channel();
                        logger.info("***********初始化channel完成************");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    void setSendQueue(BlockingQueue<String> sendQueue) {
        this.sendQueue = sendQueue;
    }

    void setRecvQueue(BlockingQueue<String> recvQueue) {
        this.recvQueue = recvQueue;
    }


    public void start() {
        for (int i = 0; i < 128; i++) {
            handleMessageThread();
        }
    }

    private void handleMessageThread() {
        new Thread(() -> {
            RpcClient rpcClient = new RpcClient();
            while (true) {
                try {
                    String[] msgStr = recvQueue.take().split("#");
                    //调用dubbo处理
                    Object result = rpcClient.simpleInvoke(msgStr[1]);
                    String r = new String((byte[]) result).replaceFirst("\n", "");
                    channel.writeAndFlush(msgStr[0] + "#" + r);
//                    sendQueue.put(msgStr[0] + "#" + r);
                    logger.info("******recvQueue.size = " + recvQueue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}