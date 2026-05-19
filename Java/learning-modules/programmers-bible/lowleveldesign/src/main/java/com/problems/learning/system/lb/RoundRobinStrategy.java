package com.problems.learning.system.lb;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Round Robin strategy: distributes requests sequentially across servers.
 *
 * Server selection cycles through the list in order:
 * S1 → S2 → S3 → S1 → S2 → ...
 */
public class RoundRobinStrategy implements LoadBalancingStrategy {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server selectServer(List<Server> servers) {
        int index = counter.getAndIncrement() % servers.size();
        // Handle integer overflow turning counter negative
        if (index < 0) {
            index += servers.size();
        }
        return servers.get(index);
    }
}

