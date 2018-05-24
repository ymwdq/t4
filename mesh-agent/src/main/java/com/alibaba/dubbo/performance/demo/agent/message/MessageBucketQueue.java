package com.alibaba.dubbo.performance.demo.agent.message;


import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;

import java.util.LinkedList;

/**
 * 考虑用一个线程负责发送 MessagaBucket 里的消息，但是其他线程需要往里添加 messageBucket，所以要注意线程安全问题
 * 拿到de消息可能为空，由调用者自己处理
 */

public class MessageBucketQueue {
    private LinkedList<MessageBucket> queue = new LinkedList<>();
    private SpinLock spinLock = new SpinLock();
    public void offer(MessageBucket messageBucket) {
        spinLock.lock();
        queue.offer(messageBucket);
        spinLock.unlock();
    }

    public MessageBucket poll() {
        spinLock.lock();
        MessageBucket messageBucket;
        if (size() == 0) {
            return null;
        }
        messageBucket = queue.poll();
        spinLock.unlock();
        return messageBucket;
    }

    public int size() {
        return queue.size();
    }

}
