package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

import java.util.Map;
import java.util.Set;

/**
 * PROBLEM SOLVED:
 * Authorization (what you can DO) is distinct from Authentication (who you ARE).
 * Keeping them in separate handlers means role-based access rules
 * can be changed without touching token validation logic and vice versa.
 */
public class AuthorizationHandler extends RequestHandler {

    // Role → allowed path prefixes
    private static final Map<String, Set<String>> ROLE_PERMISSIONS = Map.of(
            "ADMIN", Set.of("/api/admin", "/api/orders", "/api/users", "/api/health"),
            "USER", Set.of("/api/orders", "/api/profile", "/api/health"),
            "GUEST", Set.of("/api/health", "/api/products")
    );

    @Override
    public HttpResponse handle(HttpRequest request) {
        String role = request.getUserRole();
        String path = request.getPath();

        log("AuthorizationHandler", "Checking role '" + role + "' for path: " + path);

        Set<String> allowedPaths = ROLE_PERMISSIONS.getOrDefault(role, Set.of());

        boolean authorized = allowedPaths.stream()
                .anyMatch(path::startsWith);

        if (!authorized) {
            log("AuthorizationHandler", "❌ Role '" + role
                    + "' not allowed on " + path + " — rejecting");
            return new HttpResponse(403,
                    "Forbidden: role '" + role + "' cannot access " + path,
                    "AuthorizationHandler");
        }

        log("AuthorizationHandler", "✅ Authorized — passing forward");
        return passToNext(request);
    }
}