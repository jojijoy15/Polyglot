package com.problems.learning.system.lb;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Load Balancer that distributes incoming requests across a pool of backend servers
 * using a pluggable {@link LoadBalancingStrategy}.
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Pluggable strategies: Round Robin, Weighted Round Robin, Least Connections, Random</li>
 *   <li>Dynamic server pool management (add/remove at runtime)</li>
 *   <li>Health check support (mark servers up/down)</li>
 *   <li>Active connection tracking per server</li>
 *   <li>Thread-safe operations using CopyOnWriteArrayList</li>
 * </ul>
 *
 * <h2>Design Patterns Used</h2>
 * <ul>
 *   <li><b>Strategy Pattern</b> — LoadBalancingStrategy interface with multiple implementations</li>
 * </ul>
 */
public class LoadBalancer {

    private final CopyOnWriteArrayList<Server> servers;
    private LoadBalancingStrategy strategy;

    public LoadBalancer(LoadBalancingStrategy strategy) {
        this.servers = new CopyOnWriteArrayList<>();
        this.strategy = strategy;
    }

    // ──────────────────────── Server Pool Management ────────────────────────

    /**
     * Adds a server to the pool.
     */
    public void addServer(Server server) {
        if (server == null) {
            throw new IllegalArgumentException("Server cannot be null");
        }
        servers.addIfAbsent(server);
    }

    /**
     * Removes a server from the pool by its id.
     */
    public void removeServer(String serverId) {
        servers.removeIf(s -> s.getId().equals(serverId));
    }

    /**
     * Returns all servers in the pool.
     */
    public List<Server> getAllServers() {
        return List.copyOf(servers);
    }

    /**
     * Returns only healthy servers.
     */
    public List<Server> getHealthyServers() {
        return servers.stream()
                .filter(Server::isHealthy)
                .collect(Collectors.toList());
    }

    // ──────────────────────── Request Routing ────────────────────────

    /**
     * Routes a request to a healthy server using the current strategy.
     *
     * @param request the incoming request
     * @return the server selected to handle the request
     * @throws NoHealthyServerException if no healthy server is available
     */
    public Server routeRequest(Request request) {
        List<Server> healthyServers = getHealthyServers();
        if (healthyServers.isEmpty()) {
            throw new NoHealthyServerException(
                    "No healthy servers available to handle request: " + request.getRequestId());
        }

        Server selected = strategy.selectServer(healthyServers);
        selected.incrementConnections();
        return selected;
    }

    /**
     * Releases a connection from a server after request completion.
     */
    public void releaseConnection(Server server) {
        server.decrementConnections();
    }

    // ──────────────────────── Health Management ────────────────────────

    /**
     * Marks a server as healthy (up).
     */
    public void markServerUp(String serverId) {
        findServerById(serverId).ifPresent(Server::markUp);
    }

    /**
     * Marks a server as unhealthy (down).
     */
    public void markServerDown(String serverId) {
        findServerById(serverId).ifPresent(Server::markDown);
    }

    // ──────────────────────── Strategy Management ────────────────────────

    /**
     * Swaps the load balancing strategy at runtime.
     */
    public void setStrategy(LoadBalancingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public LoadBalancingStrategy getStrategy() {
        return strategy;
    }

    // ──────────────────────── Internal ────────────────────────

    private Optional<Server> findServerById(String serverId) {
        return servers.stream()
                .filter(s -> s.getId().equals(serverId))
                .findFirst();
    }
}

