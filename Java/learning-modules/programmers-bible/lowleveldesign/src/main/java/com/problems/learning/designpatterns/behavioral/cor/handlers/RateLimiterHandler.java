package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * PROBLEM SOLVED:
 * Rate limiting is independent of authentication and authorization.
 * Placing it as a separate handler means:
 * - Rate limit logic doesn't pollute auth or business logic
 * - You can place it anywhere in the chain (before or after auth)
 * - You can swap in a Redis-backed rate limiter without touching other handlers
 */
public class RateLimiterHandler extends RequestHandler {

    private static final int MAX_REQUESTS_PER_WINDOW = 3; // low threshold for demo
    private final Map<String, Integer> requestCounts = new HashMap<>();

    @Override
    public HttpResponse handle(HttpRequest request) {
        String ip = request.getClientIp();
        log("RateLimiterHandler", "Checking rate limit for IP: " + ip);

        int count = requestCounts.getOrDefault(ip, 0) + 1;
        requestCounts.put(ip, count);

        if (count > MAX_REQUESTS_PER_WINDOW) {
            log("RateLimiterHandler", "❌ Rate limit exceeded (" + count
                    + "/" + MAX_REQUESTS_PER_WINDOW + ") — blocking");
            return new HttpResponse(429, "Too Many Requests — slow down", "RateLimiterHandler");
        }

        log("RateLimiterHandler", "✅ Within limit (" + count
                + "/" + MAX_REQUESTS_PER_WINDOW + ") — passing forward");
        return passToNext(request);
    }
}