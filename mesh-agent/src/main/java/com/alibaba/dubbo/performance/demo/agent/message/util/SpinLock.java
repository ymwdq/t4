package com.alibaba.dubbo.performance.demo.agent.message.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简易自旋锁
 */
public class SpinLock {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void lock() {
        while (true) {
            if (atomicInteger.compareAndSet(0, 1)) {
                return;
            }
        }
    }

    public void unlock() {
        atomicInteger.set(0);
    }
}
