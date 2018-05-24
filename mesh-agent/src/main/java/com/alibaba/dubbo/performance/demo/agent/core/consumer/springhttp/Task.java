package com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.Unpooled.copiedBuffer;

public class Task {
    private ChannelHandlerContext ctx;
    private int id;

    public Task(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setResult(String obj) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                copiedBuffer(obj.getBytes())
        );
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
                obj.length());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        response.headers().set(HttpHeaderNames.KEEP_ALIVE)
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        this.ctx.writeAndFlush(response);
//        if (ctx.executor().inEventLoop()) {
//            ctx.writeAndFlush(response);
//        } else {
//            ctx.executor().submit(() -> {
//                ctx.writeAndFlush(response);
//            });
//        }
    }

//    public void printSendTime() {
//        System.out.println("" + this.getId() + "send: " + System.currentTimeMillis());
//    }
//
//    public void printRevTime() {
//        System.out.println("" + this.getId() + "recv: " + System.currentTimeMillis());
//    }

}
