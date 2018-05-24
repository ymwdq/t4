package com.alibaba.dubbo.performance.demo.agent.core.consumer.springhttp;

import java.util.Stack;

/**
 * Created by ym on 18-5-9.
 */
public class IdGenerator {
    private static int DEFAULT_CAPACITY = 3000;

    // safe thread
    private static Stack<Integer> freeId = new Stack<>();

    public static int getId() {
        return freeId.pop();
    }

    public static void freeId(int back) {
        freeId.push(back);
    }

    static {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            freeId.push(i);
        }
    }
}