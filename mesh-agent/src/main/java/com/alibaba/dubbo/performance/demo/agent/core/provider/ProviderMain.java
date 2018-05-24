package com.alibaba.dubbo.performance.demo.agent.core.provider;

import com.alibaba.dubbo.performance.demo.agent.core.provider.NettyHelper.NettyServerStart;

public class ProviderMain {
    public static void start() {
        NettyServerStart.startNettyServer();
    }
}
