package com.problems.learning.designpatterns.behavioral.cor.carriers;

/**
 * Represents the result of processing a request through the chain.
 * Handlers write to this when they accept or reject a request.
 */
public class HttpResponse {

    private int statusCode;
    private String body;
    private String handledBy;       // which handler produced this response
    private boolean fromCache;

    public HttpResponse(int statusCode, String body, String handledBy) {
        this.statusCode = statusCode;
        this.body = body;
        this.handledBy = handledBy;
        this.fromCache = false;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    @Override
    public String toString() {
        return String.format("HTTP %d | Handler: %-22s | Cache: %-5s | Body: %s",
                statusCode, handledBy, fromCache, body);
    }
}