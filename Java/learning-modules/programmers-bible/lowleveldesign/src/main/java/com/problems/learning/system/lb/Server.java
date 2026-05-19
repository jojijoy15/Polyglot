package com.problems.learning.system.lb;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a backend server in the load balancer pool.
 */
public class Server {

    private final String id;
    private final String ipAddress;
    private final int port;
    private int weight;
    private final AtomicInteger activeConnections;
    private volatile boolean healthy;

    public Server(String id, String ipAddress, int port, int weight) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
        this.weight = weight;
        this.activeConnections = new AtomicInteger(0);
        this.healthy = true;
    }

    public Server(String id, String ipAddress, int port) {
        this(id, ipAddress, port, 1);
    }

    public String getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getActiveConnections() {
        return activeConnections.get();
    }

    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.updateAndGet(current -> Math.max(0, current - 1));
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void markUp() {
        this.healthy = true;
    }

    public void markDown() {
        this.healthy = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(id, server.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", ip='" + ipAddress + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", connections=" + activeConnections.get() +
                ", healthy=" + healthy +
                '}';
    }
}

