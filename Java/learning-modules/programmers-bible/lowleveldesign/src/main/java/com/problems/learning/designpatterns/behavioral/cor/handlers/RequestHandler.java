package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

/**
 * PROBLEM SOLVED:
 * Without Chain of Responsibility, request processing would be a
 * massive nested if-else or a monolithic method:
 * <p>
 * if (!authenticated)  → reject
 * else if (rateLimited) → reject
 * else if (!authorized) → reject
 * else if (inCache)     → return cached
 * else                  → process
 * <p>
 * Problems with this:
 * 1. All logic crammed into one class — impossible to test independently
 * 2. Adding a new check (e.g. IP blacklist) requires modifying the monolith
 * 3. You cannot reorder or skip handlers at runtime
 * 4. Handlers cannot be reused across different pipelines
 * <p>
 * Chain of Responsibility solves this by turning each check into
 * an independent handler linked in a chain. Each handler:
 * - Does its job
 * - Either stops the chain (reject/short-circuit) or passes forward
 * - Has no knowledge of what comes before or after it
 */
public abstract class RequestHandler {

    // The next handler in the chain — null means end of chain
    private RequestHandler next;

    /**
     * PROBLEM SOLVED:
     * Fluent chaining via setNext() lets you build any pipeline
     * configuration at runtime without hardcoding the order
     * inside the handlers themselves.
     * <p>
     * auth.setNext(rateLimiter).setNext(authorizer).setNext(cache)
     */
    public RequestHandler setNext(RequestHandler next) {
        this.next = next;
        return next;   // return next for fluent chaining
    }

    /**
     * PROBLEM SOLVED:
     * This method is the backbone of the pattern.
     * Each concrete handler calls passToNext() when it wants to
     * forward the request. If there is no next handler, the chain
     * ends and returns null — signaling the request fell through
     * all handlers without a definitive response.
     */
    protected HttpResponse passToNext(HttpRequest request) {
        if (next != null) {
            return next.handle(request);
        }
        // End of chain — no handler produced a response
        return new HttpResponse(500, "No handler processed the request", "Chain-End");
    }

    /**
     * Each concrete handler implements this — decides to handle
     * the request itself or delegate to the next handler.
     */
    public abstract HttpResponse handle(HttpRequest request);

    protected void log(String handlerName, String message) {
        System.out.printf("   [%-22s] %s%n", handlerName, message);
    }
}