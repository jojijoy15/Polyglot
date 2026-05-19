package com.problems.learning.system.lb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Weighted Round Robin strategy: servers with higher weight receive proportionally more requests.
 *
 * Internally expands the server list based on weights and cycles through the expanded list.
 * Example: S1(weight=3), S2(weight=1) → expanded: [S1, S1, S1, S2]
 */
public class WeightedRoundRobinStrategy implements LoadBalancingStrategy {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server selectServer(List<Server> servers) {
        List<Server> expandedList = buildExpandedList(servers);
        int index = counter.getAndIncrement() % expandedList.size();
        if (index < 0) {
            index += expandedList.size();
        }
        return expandedList.get(index);
    }

    private List<Server> buildExpandedList(List<Server> servers) {
        List<Server> expanded = new ArrayList<>();
        for (Server server : servers) {
            int weight = Math.max(1, server.getWeight());
            for (int i = 0; i < weight; i++) {
                expanded.add(server);
            }
        }
        return expanded;
    }
}

