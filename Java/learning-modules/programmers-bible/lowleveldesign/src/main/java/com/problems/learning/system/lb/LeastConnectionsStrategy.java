package com.problems.learning.system.lb;

import java.util.Comparator;
import java.util.List;

/**
 * Least Connections strategy: routes requests to the server with the fewest active connections.
 *
 * This minimizes load on busy servers and is ideal for requests with varying processing times.
 */
public class LeastConnectionsStrategy implements LoadBalancingStrategy {

    @Override
    public Server selectServer(List<Server> servers) {
        return servers.stream()
                .min(Comparator.comparingInt(Server::getActiveConnections))
                .orElseThrow(() -> new IllegalStateException("No servers available"));
    }
}

