package com.problems.learning.designpatterns.behavioral.cor.client;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.pipeline.RequestPipeline;

public class RequestProcessingClient {
    public static void main(String[] args) {

        RequestPipeline pipeline = new RequestPipeline();

        // ── Scenario 1: Valid user request ─────────────────────────────
        System.out.println("\n===== Scenario 1: Valid USER fetching orders =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/orders", "token-user-abc123", "192.168.1.1", "USER", ""));

        // ── Scenario 2: Same request again — should HIT cache ──────────
        System.out.println("\n===== Scenario 2: Same request — Cache HIT =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/orders", "token-user-abc123", "192.168.1.1", "USER", ""));

        // ── Scenario 3: Missing token — rejected at Auth ───────────────
        System.out.println("\n===== Scenario 3: No token — Auth failure =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/orders", "", "192.168.1.2", "USER", ""));

        // ── Scenario 4: USER accessing admin — rejected at Authz ───────
        System.out.println("\n===== Scenario 4: USER accessing /api/admin — Forbidden =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/admin/users", "token-user-abc123", "192.168.1.3", "USER", ""));

        // ── Scenario 5: ADMIN accessing admin — allowed ─────────────────
        System.out.println("\n===== Scenario 5: ADMIN accessing /api/admin — Allowed =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/admin/users", "token-admin-xyz789", "192.168.1.4", "ADMIN", ""));

        // ── Scenario 6: Rate limit exceeded ────────────────────────────
        System.out.println("\n===== Scenario 6: Rate limit exceeded (same IP, 4th request) =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/orders", "token-user-abc123", "192.168.1.1", "USER", ""));

        // ── Scenario 7: Public path — bypasses auth entirely ───────────
        System.out.println("\n===== Scenario 7: Public health check — no token needed =====");
        pipeline.process(new HttpRequest(
                "GET", "/api/health", "", "10.0.0.1", "GUEST", ""));
    }
}