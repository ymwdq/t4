package com.alibaba.dubbo.performance.demo.agent.core.provider;

import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ProviderMessageQueueManager {
    private volatile BlockingQueue<String> sendQueue;
    private volatile BlockingQueue<String> recvQueue;
//    private ProviderDispatchServiceImpl providerDispatchService;
    private ProviderRecvServiceImpl providerRecvService;


    public ProviderMessageQueueManager(IRegistry registry) {
        recvQueue = new ArrayBlockingQueue<>(500);
        sendQueue = new ArrayBlockingQueue<>(500);
        providerRecvService = new ProviderRecvServiceImpl(registry);
        providerRecvService.setRecvQueue(recvQueue);
        providerRecvService.setSendQueue(sendQueue);
        providerRecvService.start();
//        providerDispatchService = new ProviderDispatchServiceImpl(registry);
//        providerDispatchService.setSendQueue(sendQueue);
//        providerDispatchService.start();
    }

    public BlockingQueue<String> getSendQueue() {
        return sendQueue;
    }


    public void offerSendQueue(String msg) {
        try {
            sendQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void offerRecvQueue(String msg) {
        try {
            recvQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}