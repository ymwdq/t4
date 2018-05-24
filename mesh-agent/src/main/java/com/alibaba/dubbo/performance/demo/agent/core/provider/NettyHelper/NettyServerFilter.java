//package com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper;
//
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpResponseEncoder;
//
//
///**
// * Title: NettyServerFilter
// * Description: Netty 服务端过滤器
// * Version:1.0.0
// *
// * @author pancm
// * @date 2017年10月26日
// */
//public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
//
//    @Override
//    protected void initChannel(SocketChannel socketChannel) {
//        ChannelPipeline pipeline = socketChannel.pipeline();
//        //处理http服务的关键handler
//        pipeline.addLast("encoder", new HttpResponseEncoder());
//        pipeline.addLast("decoder", new HttpRequestDecoder());
//        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
//        pipeline.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
//    }
//}
