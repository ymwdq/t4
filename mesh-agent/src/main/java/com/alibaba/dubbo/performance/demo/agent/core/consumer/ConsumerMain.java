package com.alibaba.dubbo.performance.demo.agent.core.consumer;

import com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhelper.NettyServerStart;
import com.alibaba.dubbo.performance.demo.agent.core.consumer.nettyhttp.ConsumerHttpServer;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;


//@SpringBootApplication
public class ConsumerMain {
    private static IRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));
    private static final int PORT = Integer.valueOf(System.getProperty("server.port"));
    private static final String serviceName = "consumer";
    public static ConsumerMessageQueueManager messageManager;

    static {
        try {
            System.out.println("=================NettyHttpServerHandler====================");
            registry.register(serviceName, 20001);
            messageManager = new ConsumerMessageQueueManager(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start() {

        new Thread(NettyServerStart::startNettyServer).start();
//        new Thread(NettyServerStart::startNettyHttpServer).start();
        new Thread(() -> {
            ConsumerHttpServer consumerHttpServer = new ConsumerHttpServer(20000);
            try {
                consumerHttpServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
//        SpringApplication.run(ConsumerMain.class,args);
    }
}
