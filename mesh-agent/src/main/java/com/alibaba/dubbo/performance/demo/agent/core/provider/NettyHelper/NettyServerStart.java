package com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper;

import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerStart {
    public static final int port = Integer.valueOf(System.getProperty("server.port"));

    private static EtcdRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));
    private static final String serviceName = System.getProperty("type");

    static {
        try {
            //向Etcd注册
            registry.register(serviceName, port);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void startNettyServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new HelloServerInitializer()); //设置过滤器
            // 服务器绑定端口监听
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("NettyServer服务端启动成功,端口是:" + port);
            // 监听服务器关闭监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
