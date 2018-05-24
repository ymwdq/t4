package com.alibaba.dubbo.performance.demo.agent.registry;

import java.net.InetAddress;

class IpHelper {


    static String getHostIp() throws Exception {
        return InetAddress.getLocalHost().getHostAddress();
//        return System.getProperty("server.ip");
//        return "10.108.115.70";
    }
}
