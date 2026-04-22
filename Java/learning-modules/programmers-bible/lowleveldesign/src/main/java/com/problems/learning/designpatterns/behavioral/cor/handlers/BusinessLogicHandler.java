package com.problems.learning.designpatterns.behavioral.cor.handlers;

import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpRequest;
import com.problems.learning.designpatterns.behavioral.cor.carriers.HttpResponse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class BusinessLogicHandler extends RequestHandler {

    /**
     * Route registry — path prefix mapped to its handler function.
     * LinkedHashMap preserves insertion order so more specific routes
     * can be registered before broader ones if needed.
     * Adding a new route = one new entry here, nothing else changes.
     */
    private final Map<String, Function<HttpRequest, String>> routes = new LinkedHashMap<>();

    public BusinessLogicHandler() {
        routes.put("/api/admin", this::handleAdmin);
        routes.put("/api/orders", this::handleOrders);
        routes.put("/api/profile", this::handleProfile);
        routes.put("/api/products", this::handleProducts);
        routes.put("/api/health", this::handleHealth);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        String path = request.getPath();
        log("BusinessLogicHandler", "Routing: " + path);

        return routes.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .findFirst()
                .map(entry -> {
                    String body = entry.getValue().apply(request);
                    log("BusinessLogicHandler", "✅ Handled by route: " + entry.getKey());
                    return new HttpResponse(200, body, "BusinessLogicHandler");
                })
                .orElseGet(() -> {
                    log("BusinessLogicHandler", "❌ No route matched: " + path);
                    return new HttpResponse(404, "Not Found: " + path, "BusinessLogicHandler");
                });
    }

    private String handleOrders(HttpRequest req) {
        return req.getMethod().equals("GET")
                ? "{orders: [{id:1, item:'Laptop'}, {id:2, item:'Phone'}]}"
                : "{message: 'Order created successfully'}";
    }

    private String handleAdmin(HttpRequest req) {
        return "{users: [{id:1, name:'Alice'}, {id:2, name:'Bob'}]}";
    }

    private String handleProfile(HttpRequest req) {
        return "{profile: {name:'Joji', role:'USER', joined:'2024-01-01'}}";
    }

    private String handleProducts(HttpRequest req) {
        return "{products: [{id:1, name:'Laptop', price:1200}]}";
    }

    private String handleHealth(HttpRequest req) {
        return "{status: 'UP', version: '1.0.0'}";
    }
}