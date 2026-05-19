package com.problems.learning.system.lb;

import java.util.List;

/**
 * Strategy interface for load balancing algorithms.
 * Each implementation encapsulates a different server selection policy.
 */
public interface LoadBalancingStrategy {

    /**
     * Selects the next server from the given list of healthy servers.
     *
     * @param servers list of healthy, available servers
     * @return the selected server to route the request to
     */
    Server selectServer(List<Server> servers);
}

