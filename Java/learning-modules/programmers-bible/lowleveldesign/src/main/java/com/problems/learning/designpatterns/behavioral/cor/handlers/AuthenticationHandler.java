package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

import java.util.Set;

/**
 * PROBLEM SOLVED:
 * Authentication is a cross-cutting concern — it should run for
 * every request regardless of what endpoint is being called.
 * Isolating it here means:
 * - Auth logic is testable in isolation
 * - Bypassing auth in tests is as simple as removing it from the chain
 * - Auth failures short-circuit the chain — no downstream handler runs
 */
public class AuthenticationHandler extends RequestHandler {

    // Simulated token store (in real life: JWT validation or session store)
    private static final Set<String> VALID_TOKENS = Set.of(
            "token-user-abc123",
            "token-admin-xyz789",
            "token-guest-def456"
    );

    // Paths that bypass authentication entirely
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/health",
            "/api/login",
            "/api/register"
    );

    @Override
    public HttpResponse handle(HttpRequest request) {
        log("AuthenticationHandler", "Checking authentication for: " + request.getPath());

        // Public paths skip auth — pass straight to next handler
        if (PUBLIC_PATHS.contains(request.getPath())) {
            log("AuthenticationHandler", "✅ Public path — skipping auth");
            return passToNext(request);
        }

        // No token provided
        if (request.getAuthToken() == null || request.getAuthToken().isBlank()) {
            log("AuthenticationHandler", "❌ No auth token — rejecting");
            return new HttpResponse(401, "Unauthorized: missing token", "AuthenticationHandler");
        }

        // Invalid token
        if (!VALID_TOKENS.contains(request.getAuthToken())) {
            log("AuthenticationHandler", "❌ Invalid token — rejecting");
            return new HttpResponse(401, "Unauthorized: invalid token", "AuthenticationHandler");
        }

        log("AuthenticationHandler", "✅ Token valid — passing forward");
        return passToNext(request);
    }
}