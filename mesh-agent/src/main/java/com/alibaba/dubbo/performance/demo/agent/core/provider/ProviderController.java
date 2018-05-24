//package com.alibaba.dubbo.performance.demo.agent.core.provider;
//
//import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
//import com.alibaba.dubbo.performance.demo.agent.message.util.MessageUtil;
//import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.Callable;
//
//
//@RestController
//public class ProviderController {
//    private static EtcdRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));
//    private static ProviderMessageQueueManager providerMessageQueueManager = new ProviderMessageQueueManager(registry);
//    public static final int servicePort = Integer.valueOf(System.getProperty("server.port"));
//    private static final String serviceName = "provider";
//
//    static {
//        try {
//            registry.register(serviceName, servicePort);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "/agent")
//    public Callable<String> getResult(@RequestParam("msg") String msgString) {
//        System.out.println("收到请求时间："+System.currentTimeMillis());
//        return () -> {
//            Message msg = MessageUtil.stringToMsg(msgString);
//            providerMessageQueueManager.offerRecvQueue(msg);
//            return "OK";
//        };
//    }
//
//    @RequestMapping(value = "/agent_single")
//    public String singleCall(@RequestParam("msg") String msgString) {
//            Message msg = MessageUtil.stringToMsg(msgString);
//            providerMessageQueueManager.offerRecvQueue(msg);
//            return "OK";
//    }
//
//
//}
