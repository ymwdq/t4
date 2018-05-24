//package com.alibaba.dubbo.performance.demo.agent.message;
//
//import com.alibaba.dubbo.performance.demo.agent.core.httpserver.HttpHandler;
//import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
//import com.alibaba.dubbo.performance.demo.agent.message.model.MessageQueueImpl;
//import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 定义消息管理中心，维护两个消息队列。一个发送队列，用于缓存发送消息。另一个为结果队列，缓存 provider-agent 返回的结果，每次返回结果都通知相应对handler
// */
//
//public class MessageCenter {
//    private MessageQueueImpl sendQueue;
//    private MessageQueueImpl recvQueue;
//    private List<HttpHandler> handlers = new ArrayList<>();
//    private SpinLock sendLock = new SpinLock();
//    private SpinLock recvLock = new SpinLock();
//    private SpinLock registeryLock = new SpinLock();
//
//    public void registery(HttpHandler httpHandler) {
//        registeryLock.lock();
//        handlers.add(httpHandler);
//        registeryLock.unlock();
//    }
//
//    public void addSendMsg(Message msg) {
//        sendQueue.offer(msg);
//    }
//
//    public void addRecvMsg(Message msg) {
//        recvQueue.offer(msg);
//    }
//
//    private Message pollRecvMsg(Message msg) {
//        return recvQueue.poll();
//    }
//
//    private void notifyHandlers(Message msg) {
//        registeryLock.lock();
//        for (HttpHandler httpHandler : handlers) {
//            httpHandler.checkAndWrite(msg);
//        }
//        registeryLock.unlock();
//    }
//
//    static class SendMsgThread implements Runnable {
//        @Override
//        public void run() {
//
//        }
//    }
//
//
//    class DispatchMsgThread implements Runnable {
//        @Override
//        public void run() {
//            Message recvMsg = null;
//            while (recvMsg == null) {
//                recvMsg = recvQueue.poll();
//            }
//            notifyHandlers(recvMsg);
//        }
//    }
//
//}
//
