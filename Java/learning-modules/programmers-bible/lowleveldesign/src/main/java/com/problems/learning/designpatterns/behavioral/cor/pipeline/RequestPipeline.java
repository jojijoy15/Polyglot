package com.problems.learning.designpatterns.behavioral.cor.pipeline;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;
import com.problems.learning.designpatterns.behavioral.cor.handlers.*;

/**
 * PROBLEM SOLVED:
 * Chain construction is centralized here — the client never
 * manually links handlers. Changing the pipeline order
 * or adding/removing a handler is a one-line change here,
 * with zero impact on any handler class.
 */
public class RequestPipeline {

    private final RequestHandler chain;

    public RequestPipeline() {
        // Build the chain — order matters!
        AuthenticationHandler auth = new AuthenticationHandler();
        RateLimiterHandler rateLimiter = new RateLimiterHandler();
        AuthorizationHandler authorizer = new AuthorizationHandler();
        CacheHandler cache = new CacheHandler();
        BusinessLogicHandler business = new BusinessLogicHandler();

        // Fluent chain construction
        // Auth → RateLimit → Authorize → Cache → Business
        auth.setNext(rateLimiter)
                .setNext(authorizer)
                .setNext(cache)
                .setNext(business);

        this.chain = auth; // entry point of the chain
    }

    public HttpResponse process(HttpRequest request) {
        System.out.println("\n┌─────────────────────────────────────────────────");
        System.out.println("│ Incoming: " + request);
        System.out.println("├─────────────────────────────────────────────────");
        HttpResponse response = chain.handle(request);
        System.out.println("├─────────────────────────────────────────────────");
        System.out.println("│ Response: " + response);
        System.out.println("└─────────────────────────────────────────────────");
        return response;
    }
}