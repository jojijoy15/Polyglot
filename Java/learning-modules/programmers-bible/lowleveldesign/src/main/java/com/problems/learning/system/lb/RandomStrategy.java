package com.problems.learning.system.lb;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random strategy: selects a server at random from the healthy pool.
 *
 * Simple and effective when servers are homogeneous and request load is uniform.
 */
public class RandomStrategy implements LoadBalancingStrategy {

    @Override
    public Server selectServer(List<Server> servers) {
        int index = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(index);
    }
}

