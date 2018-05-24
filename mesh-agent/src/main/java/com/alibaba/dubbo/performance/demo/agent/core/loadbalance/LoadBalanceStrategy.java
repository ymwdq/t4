package com.alibaba.dubbo.performance.demo.agent.core.loadbalance;

import java.util.List;

public interface LoadBalanceStrategy {
    Status select(List<Status> statusList);
}
