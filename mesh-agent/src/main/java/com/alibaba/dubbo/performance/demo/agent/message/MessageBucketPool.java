package com.alibaba.dubbo.performance.demo.agent.message;


import com.alibaba.dubbo.performance.demo.agent.message.util.SpinLock;

import java.util.Stack;


public class MessageBucketPool {
    protected Stack<MessageBucket> freeBucket = new Stack<>();

    public MessageBucket getBucket() {
        MessageBucket r;
        if (freeBucket.empty()) {
            r = new MessageBucket();
        } else {
            r = freeBucket.pop();
        }
        return r;
    }

    public void freeBucket(MessageBucket messageBucket) {
        freeBucket.push(messageBucket);
    }

}
