package com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhttp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ConsumerHttpServer {

    private final int PORT;

    public ConsumerHttpServer(int port) {
        PORT = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ConsumerHttpServerInitializer());
            b.option(ChannelOption.SO_KEEPALIVE, true);
//            Channel ch = b.bind(PORT).sync().channel();
//            System.err.println("Open your web browser and navigate to http://127.0.0.1:" + PORT + '/');
//            ch.closeFuture().sync();
            Channel ch = b.bind(PORT).sync().channel();
            System.err.println("Open your web browser and navigate to " + "://127.0.0.1:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

//    public static void main(String[] args) {
//        try {
//            new ConsumerHttpServer(3000).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
