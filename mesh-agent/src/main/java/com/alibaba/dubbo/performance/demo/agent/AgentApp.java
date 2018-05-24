package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.ConsumerMain;
import com.alibaba.dubbo.performance.demo.agent.core.provider.ProviderMain;

public class AgentApp {
    public static void main(String[] args) {
        String type = System.getProperty("type");
        if (type.equals("consumer")) {
            ConsumerMain.start();
        } else {
            ProviderMain.start();
        }
    }
}
