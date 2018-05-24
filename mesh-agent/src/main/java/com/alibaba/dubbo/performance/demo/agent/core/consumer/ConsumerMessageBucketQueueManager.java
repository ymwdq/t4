//package com.alibaba.dubbo.performance.demo.agent.core.consumer;
//
//
//import com.alibaba.dubbo.performance.demo.agent.message.MessageBucket;
//import com.alibaba.dubbo.performance.demo.agent.message.MessageBucketPool;
//import com.alibaba.dubbo.performance.demo.agent.message.MessageBucketQueue;
//import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;
//
//import java.util.LinkedList;
//
//public class ConsumerMessageBucketQueueManager {
//    private static volatile MessageBucketQueue sendQueue = new MessageBucketQueue();
//    private static volatile MessageBucketQueue recvQueue = new MessageBucketQueue();
//    private static LinkedList<MessageBucket> freeBuckets = new LinkedList<>();
//    private static SpinLock lock = new SpinLock();
//    public static MessageBucketQueue getSendQueue() {
//        return sendQueue;
//    }
//
//    /**
//     * 使用自旋锁可能死锁?
//     * @return
//     */
//    public static MessageBucket getSendBucket() {
//        lock.lock();
//        MessageBucket r;
//        if (freeBuckets.size() == 0) {
//            return new MessageBucket();
//        } else {
//            r = freeBuckets.pop();
//
//        }
//        lock.unlock();
//        return r;
//    }
//
//    public static void freeSendBucket(MessageBucket messageBucket) {
//        lock.lock();
//        freeBuckets.add(messageBucket);
//        lock.unlock();
//    }
//
//
//    /**
//     * 消息池，线程本地变量存储
//     */
//    public static class ThreadLocalMessagePool {
//        private ThreadLocalMessagePool() {}
//        private static final ThreadLocal<ThreadMessageBucketProxy> local = new ThreadLocal<ThreadMessageBucketProxy>() {
//            @Override
//            protected ThreadMessageBucketProxy initialValue() {
//                return new ThreadMessageBucketProxy();
//            }
//        };
//
//        public static ThreadLocal<ThreadMessageBucketProxy> getLocal() {
//            return local;
//        }
//
//    }
//
//    /**
//     * 自动去消息管理中心取消息
//     */
//    public static class ThreadMessageBucketProxy {
//        private MessageBucket messageBucket;
//        public MessageBucket getBucket() {
//           return messageBucket;
//        }
//        public void refresh() {
//            messageBucket = ConsumerMessageBucketQueueManager.getSendBucket();
//        }
//    }
//}
