//package com.alibaba.dubbo.performance.demo.agent.message;
//
//
//import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
//import com.alibaba.dubbo.performance.demo.agent.message.model.MessageQueueImpl;
//import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;
//
//import java.util.LinkedList;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class SpinMessageQueue implements MessageQueueImpl {
//    private LinkedList<Message> messages = new LinkedList<>();
//    private SpinLock lock = new SpinLock();
//    private ReentrantLock reentrantLock = new ReentrantLock();
//    private Condition emptyCondition = reentrantLock.newCondition();
//
//    @Override
//    public void offer(Message msg) {
//        lock.lock();
//        messages.addLast(msg);
//        if (size() == 1) {
//            emptyCondition.notifyAll();
//        }
//        lock.unlock();
//    }
//
//
//    /**
//     * 如果队列为空，则休眠，并返回空值，此时自己可以继续自旋或者休眠一段时间再取。
//     * @return 取到的消息，可以为空值。
//     */
//    @Override
//    public Message poll() {
//        if (size() == 0) {
//            reentrantLock.lock();
//            while (size() == 0) {
//                try {
//                    emptyCondition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            reentrantLock.unlock();
//            return null;
//        }
//        lock.lock();
//        Message msg = messages.getFirst();
//        lock.unlock();
//        return msg;
//    }
//
//    @Override
//    public int size() {
//        return messages.size();
//    }
//}
