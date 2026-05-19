package com.problems.learning.system.lb;

/**
 * Represents an incoming request to be routed by the load balancer.
 */
public class Request {

    private final String requestId;
    private final String payload;

    public Request(String requestId, String payload) {
        this.requestId = requestId;
        this.payload = payload;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Request{id='" + requestId + "', payload='" + payload + "'}";
    }
}

