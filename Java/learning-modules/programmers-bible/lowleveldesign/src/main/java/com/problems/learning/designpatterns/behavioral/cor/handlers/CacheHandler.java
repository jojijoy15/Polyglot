package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * PROBLEM SOLVED:
 * Caching is a short-circuit optimization — if a cached response
 * exists, there's no reason to hit the business logic handler.
 * As a chain handler, it transparently intercepts GET requests,
 * returns cached data when available, and populates the cache
 * with responses from downstream handlers.
 * <p>
 * Adding or removing this handler from the chain enables/disables
 * caching with zero changes to any other handler.
 */
public class CacheHandler extends RequestHandler {

    private final Map<String, HttpResponse> cache = new HashMap<>();

    // Only GET requests are cacheable
    private static final Set<String> CACHEABLE_METHODS = Set.of("GET");

    @Override
    public HttpResponse handle(HttpRequest request) {
        String cacheKey = request.getMethod() + ":" + request.getPath();

        if (!CACHEABLE_METHODS.contains(request.getMethod())) {
            log("CacheHandler", "⏭️  Non-GET request — skipping cache");
            return passToNext(request);
        }

        // Cache HIT — short-circuit the chain
        if (cache.containsKey(cacheKey)) {
            log("CacheHandler", "✅ Cache HIT for: " + cacheKey + " — returning cached response");
            HttpResponse cached = cache.get(cacheKey);
            cached.setFromCache(true);
            return cached;
        }

        // Cache MISS — let the chain proceed, then cache the result
        log("CacheHandler", "❌ Cache MISS for: " + cacheKey + " — passing forward");
        HttpResponse response = passToNext(request);

        if (response != null && response.toString().contains("200")) {
            cache.put(cacheKey, response);
            log("CacheHandler", "💾 Cached response for: " + cacheKey);
        }

        return response;
    }
}