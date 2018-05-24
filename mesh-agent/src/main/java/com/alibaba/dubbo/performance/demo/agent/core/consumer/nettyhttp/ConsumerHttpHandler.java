package com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhttp;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.ConsumerMessageQueueManager;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.IdGenerator;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp.Task;
import com.alibaba.dubbo.performance.demo.agent.message.*;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


public class ConsumerHttpHandler extends ChannelInboundHandlerAdapter {

    private static ConsumerMessageQueueManager messageManager = ConsumerMessageQueueManager.getInstance();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
//            final FullHttpRequest request = (FullHttpRequest) msg;
//
//            final String responseMessage = "Hello from Netty!";
//
//            String body = getBody(request);    //获取参数
//            HttpMethod method = request.method();//获取请求方法
//            System.out.println(body);
//            System.out.println(method);
//
//            FullHttpResponse response = new DefaultFullHttpResponse(
//                    HttpVersion.HTTP_1_1,
//                    HttpResponseStatus.OK,
//                    copiedBuffer(responseMessage.getBytes())
//            );
////
//            response.headers().set(
//                    HttpHeaders.Names.CONNECTION,
//                    HttpHeaders.Values.KEEP_ALIVE
//            );

//            if (HttpHeaders.isKeepAlive(request)) {
//                response.headers().set(
//                        HttpHeaders.Names.CONNECTION,
//                        HttpHeaders.Values.KEEP_ALIVE
//                );
//            }
//            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
//                    "text/plain");
//            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
//                    responseMessage.length());

//            ctx.writeAndFlush(response);
//        }

        final FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String body = getBody(httpRequest);    //获取参数
        HttpMethod method = httpRequest.method();//获取请求方法
//            if (HttpMethod.POST.equals(method)) {
//                String responseMessage = "123124534";
//
//                    FullHttpResponse response = new DefaultFullHttpResponse(
//                    HttpVersion.HTTP_1_1,
//                    HttpResponseStatus.OK,
//                    copiedBuffer(responseMessage.getBytes())
//            );
//
//            if (HttpHeaders.isKeepAlive(httpRequest)) {
//                response.headers().set(
//                        HttpHeaders.Names.CONNECTION,
//                        HttpHeaders.Values.KEEP_ALIVE
//                );
//            }
//            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
//                    "text/plain");
//            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
//                    responseMessage.length());
//
//            ctx.writeAndFlush(response);
//                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer("123123123", CharsetUtil.UTF_8));
//                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
//                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        response.headers().set(HttpHeaderNames.KEEP_ALIVE)
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//                ctx.writeAndFlush(response);
//                System.out.println(httpRequest.toString());
//                System.out.println("body:" + body);
                Task task = new Task(ctx);
                try {
                    consumer(task, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }


        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private String getBody(FullHttpRequest request) {
        ByteBuf buf = request.content();
        String r =  buf.toString(CharsetUtil.UTF_8);
        buf.release();
        return r;
    }

    public void consumer(Task task, String parameter) throws Exception {
//        logger.info("consumer begin");
        int id = IdGenerator.getId();

        Message msg = new MessageImpl(id, parameter);
//        logger.info("" + msg.getId()+ ":" + msg.getBody());
        messageManager.offerSendQueue(msg, task);
    }

}

