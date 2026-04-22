package com.problems.learning.designpatterns.behavioral.cor.carriers;

import java.util.HashMap;
import java.util.Map;

/**
 * Carries all context about the incoming HTTP request.
 * Handlers read from this — they never modify it (immutable intent).
 */
public class HttpRequest {

    private final String method;           // GET, POST, PUT, DELETE
    private final String path;             // /api/orders, /api/admin/users
    private final String authToken;        // Bearer token from Authorization header
    private final String clientIp;         // for rate limiting
    private final String userRole;         // extracted from token: USER, ADMIN, GUEST
    private final Map<String, String> headers;
    private final String body;

    public HttpRequest(String method, String path, String authToken,
                       String clientIp, String userRole, String body) {
        this.method = method;
        this.authToken = authToken;
        this.clientIp = clientIp;
        this.path = path;
        this.userRole = userRole;
        this.body = body;
        this.headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | IP: %s | Role: %s | Token: %s",
                method, path, clientIp, userRole,
                authToken.isEmpty() ? "none" : authToken.substring(0, 10) + "...");
    }
}