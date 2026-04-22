package concurrency.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Comprehensive examples of {@link CompletableFuture} operations.
 *
 * <pre>
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │                    CompletableFuture Operation Families                    │
 * ├───────────────┬───────────────────────────────────────────────────────────  │
 * │ Create        │ supplyAsync, runAsync, completedFuture                     │
 * │ Transform     │ thenApply, thenApplyAsync                                 │
 * │ Consume       │ thenAccept, thenRun                                       │
 * │ Compose       │ thenCompose (flatMap — avoids nested futures)             │
 * │ Combine       │ thenCombine (merges two independent futures)              │
 * │ Either        │ acceptEither, applyToEither (first to complete wins)      │
 * │ All / Any     │ allOf (wait for all), anyOf (wait for first)             │
 * │ Error         │ exceptionally, handle, whenComplete                       │
 * │ Timeout       │ orTimeout, completeOnTimeout (Java 9+)                    │
 * └───────────────┴───────────────────────────────────────────────────────────  │
 * </pre>
 */
public class CompletableFutureOps {

    // ===================== 1. Creation =====================

    /** supplyAsync — runs a supplier on the ForkJoinPool.commonPool(), returns a future with the result. */
    public CompletableFuture<String> supplyAsync() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return "Hello from supplyAsync";
        });
    }

    /** runAsync — runs a Runnable asynchronously, returns CompletableFuture<Void>. */
    public CompletableFuture<Void> runAsync() {
        return CompletableFuture.runAsync(() -> {
            sleep(100);
            System.out.println("Side-effect from runAsync");
        });
    }

    /** completedFuture — creates an already-completed future. Useful for testing or default values. */
    public CompletableFuture<String> completedFuture(String value) {
        return CompletableFuture.completedFuture(value);
    }

    // ===================== 2. thenApply — Transform the result (like map) =====================

    /**
     * thenApply chains a Function that transforms the result.
     * Stage: supplyAsync("hello") → thenApply(toUpperCase) → "HELLO"
     *
     * Runs on the same thread that completed the previous stage (or the caller if already done).
     */
    public CompletableFuture<String> thenApplyExample() {
        return CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(String::toUpperCase)       // "hello" → "HELLO"
                .thenApply(s -> s + " WORLD");        // "HELLO" → "HELLO WORLD"
    }

    /**
     * thenApplyAsync — same as thenApply but the function runs on a different thread
     * from the ForkJoinPool (or a custom Executor).
     */
    public CompletableFuture<Integer> thenApplyAsyncExample() {
        return CompletableFuture
                .supplyAsync(() -> "12345")
                .thenApplyAsync(String::length);      // runs on a pool thread
    }

    // ===================== 3. thenAccept / thenRun — Consume the result =====================

    /**
     * thenAccept — consumes the result (returns Void). Use for side effects like logging.
     */
    public CompletableFuture<Void> thenAcceptExample() {
        return CompletableFuture
                .supplyAsync(() -> "result")
                .thenAccept(result -> System.out.println("Got: " + result));
    }

    /**
     * thenRun — runs a Runnable after the previous stage completes. Does NOT receive the result.
     */
    public CompletableFuture<Void> thenRunExample() {
        return CompletableFuture
                .supplyAsync(() -> "ignored")
                .thenRun(() -> System.out.println("Previous stage done, running cleanup"));
    }

    // ===================== 4. thenCompose — Flatten nested futures (like flatMap) =====================

    /**
     * thenCompose avoids CompletableFuture&lt;CompletableFuture&lt;T&gt;&gt; nesting.
     * <p>
     * Without compose: supplyAsync().thenApply(fetchDetails) → CompletableFuture&lt;CompletableFuture&lt;String&gt;&gt;
     * With compose:    supplyAsync().thenCompose(fetchDetails) → CompletableFuture&lt;String&gt;
     * <p>
     * Use case: when the transformation itself returns a CompletableFuture (e.g., another async call).
     */
    public CompletableFuture<String> thenComposeExample() {
        return CompletableFuture
                .supplyAsync(() -> 42)                           // Stage 1: get user ID
                .thenCompose(userId -> fetchUserName(userId));   // Stage 2: async lookup by ID
    }

    /** Simulates an async database/service call. */
    private CompletableFuture<String> fetchUserName(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(50);
            return "User-" + userId;
        });
    }

    // ===================== 5. thenCombine — Merge two independent futures =====================

    /**
     * thenCombine runs two independent futures in parallel and combines their results
     * when BOTH complete.
     * <p>
     * Use case: fetch price from service A and discount from service B, then compute final price.
     */
    public CompletableFuture<String> thenCombineExample() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return "Hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(80);
            return "World";
        });

        // Both run concurrently; combine once both are done
        return future1.thenCombine(future2, (r1, r2) -> r1 + " " + r2);
    }

    /**
     * Practical example: computing a final price from two independent async lookups.
     */
    public CompletableFuture<Double> thenCombinePriceExample() {
        CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return 100.0;       // base price from pricing service
        });

        CompletableFuture<Double> discountFuture = CompletableFuture.supplyAsync(() -> {
            sleep(80);
            return 0.15;        // 15% discount from discount service
        });

        return priceFuture.thenCombine(discountFuture,
                (price, discount) -> price * (1 - discount));   // 100 * 0.85 = 85.0
    }

    // ===================== 6. acceptEither / applyToEither — First to complete wins =====================

    /**
     * applyToEither — applies the function to whichever future completes first.
     * Use case: querying two replicas and using the faster response.
     */
    public CompletableFuture<String> applyToEitherExample() {
        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
            sleep(50);
            return "fast-server";
        });

        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "slow-server";
        });

        return fast.applyToEither(slow, result -> "Winner: " + result);
    }

    // ===================== 7. allOf — Wait for ALL futures to complete =====================

    /**
     * allOf returns CompletableFuture&lt;Void&gt; that completes when ALL supplied futures complete.
     * You then manually collect the results from each future.
     * <p>
     * Use case: parallel fetching of multiple resources, then aggregating results.
     */
    public CompletableFuture<List<String>> allOfExample() {
        List<CompletableFuture<String>> futures = List.of(
                CompletableFuture.supplyAsync(() -> { sleep(100); return "A"; }),
                CompletableFuture.supplyAsync(() -> { sleep(80);  return "B"; }),
                CompletableFuture.supplyAsync(() -> { sleep(60);  return "C"; })
        );

        // Wait for all, then collect results
        return CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)   // safe — all are already complete
                        .collect(Collectors.toList()));
    }

    // ===================== 8. anyOf — Wait for the FIRST future to complete =====================

    /**
     * anyOf returns CompletableFuture&lt;Object&gt; that completes as soon as ANY supplied future completes.
     * The result is the value of the first future to finish.
     */
    public CompletableFuture<Object> anyOfExample() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> { sleep(200); return "Slow"; });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> { sleep(50);  return "Fast"; });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> { sleep(150); return "Medium"; });

        return CompletableFuture.anyOf(f1, f2, f3);
    }

    // ===================== 9. Error Handling =====================

    /**
     * exceptionally — recovers from an exception by providing a fallback value.
     * Only invoked if the previous stage completed exceptionally.
     */
    public CompletableFuture<String> exceptionallyExample(boolean shouldFail) {
        return CompletableFuture
                .supplyAsync(() -> {
                    if (shouldFail) throw new RuntimeException("Boom!");
                    return "Success";
                })
                .exceptionally(ex -> "Recovered from: " + ex.getMessage());

    }

    /**
     * handle — processes BOTH success and failure in a single callback.
     * Always invoked regardless of whether the previous stage succeeded or failed.
     * <p>
     * Difference from exceptionally:
     * - exceptionally: only called on failure, receives Throwable
     * - handle: always called, receives (result, exception) — one will be null
     */
    public CompletableFuture<String> handleExample(boolean shouldFail) {
        return CompletableFuture
                .supplyAsync(() -> {
                    if (shouldFail) throw new RuntimeException("Oops!");
                    return "OK";
                })
                .handle((result, ex) -> {
                    if (ex != null) {
                        return "Handled error: " + ex.getMessage();
                    }
                    return "Handled success: " + result;
                });
    }

    /**
     * whenComplete — like handle, but does NOT transform the result.
     * Used for side-effects (logging, metrics) without changing the pipeline value.
     * If the original future failed, it stays failed after whenComplete.
     */
    public CompletableFuture<String> whenCompleteExample() {
        return CompletableFuture
                .supplyAsync(() -> "data")
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("Logging error: " + ex.getMessage());
                    } else {
                        System.out.println("Logging result: " + result);
                    }
                });
        // The return value is still "data" — whenComplete doesn't change it
    }

    // ===================== 10. Timeout (Java 9+) =====================

    /**
     * orTimeout — fails the future with TimeoutException if it doesn't complete in time.
     */
    public CompletableFuture<String> orTimeoutExample() {
        return CompletableFuture
                .supplyAsync(() -> {
                    sleep(5000); // very slow
                    return "Too late";
                })
                .orTimeout(200, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> "Timed out: " + ex.getMessage());
    }

    /**
     * completeOnTimeout — provides a default value if the future doesn't complete in time.
     * Unlike orTimeout, this does NOT throw — it quietly substitutes the default.
     */
    public CompletableFuture<String> completeOnTimeoutExample() {
        return CompletableFuture
                .supplyAsync(() -> {
                    sleep(5000); // very slow
                    return "Actual result";
                })
                .completeOnTimeout("Default (timed out)", 200, TimeUnit.MILLISECONDS);
    }

    // ===================== 11. Chaining multiple stages — real-world pipeline =====================

    /**
     * A realistic pipeline combining multiple operations:
     * <pre>
     * fetchOrderId()                          → supplyAsync
     *   .thenCompose(fetchOrderDetails)       → async DB lookup (flatMap)
     *   .thenCombine(fetchExchangeRate)       → parallel lookup, combine
     *   .thenApply(formatResult)              → transform
     *   .exceptionally(fallback)              → error recovery
     * </pre>
     */
    public CompletableFuture<String> realisticPipeline() {
        return CompletableFuture
                .supplyAsync(() -> {
                    sleep(50);
                    return 1001;    // Step 1: get order ID
                })
                .thenCompose(orderId -> CompletableFuture.supplyAsync(() -> {
                    sleep(50);
                    return "Order#" + orderId + " total=$100";   // Step 2: fetch order details
                }))
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> {
                            sleep(30);
                            return 0.85;                          // Step 3: fetch USD→EUR rate
                        }),
                        (orderDetails, rate) -> orderDetails + " (EUR: €" + (100 * rate) + ")"   // Step 4: combine
                )
                .thenApply(String::toUpperCase)                   // Step 5: format
                .exceptionally(ex -> "Pipeline failed: " + ex.getMessage());  // Step 6: fallback
    }

    // ===================== Utility =====================

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

