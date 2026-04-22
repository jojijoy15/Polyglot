package concurrency.async;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureOpsTest {

    private CompletableFutureOps ops;

    @BeforeEach
    void setUp() {
        ops = new CompletableFutureOps();
    }

    // ===================== 1. Creation =====================

    @Test
    void supplyAsync_returnsResult() throws Exception {
        String result = ops.supplyAsync().get();
        assertEquals("Hello from supplyAsync", result);
    }

    @Test
    void runAsync_completesWithVoid() throws Exception {
        Void result = ops.runAsync().get();
        assertNull(result); // runAsync returns CompletableFuture<Void>
    }

    @Test
    void completedFuture_isAlreadyDone() {
        CompletableFuture<String> cf = ops.completedFuture("instant");
        assertTrue(cf.isDone());
        assertEquals("instant", cf.join());
    }

    // ===================== 2. thenApply =====================

    @Test
    void thenApply_transformsResult() throws Exception {
        String result = ops.thenApplyExample().get();
        assertEquals("HELLO WORLD", result);
    }

    @Test
    void thenApplyAsync_transformsOnDifferentThread() throws Exception {
        int result = ops.thenApplyAsyncExample().get();
        assertEquals(5, result); // "12345".length()
    }

    // ===================== 3. thenAccept / thenRun =====================

    @Test
    void thenAccept_consumesResultAndReturnsVoid() throws Exception {
        Void result = ops.thenAcceptExample().get();
        assertNull(result);
    }

    @Test
    void thenRun_runsAfterCompletion() throws Exception {
        Void result = ops.thenRunExample().get();
        assertNull(result);
    }

    // ===================== 4. thenCompose (flatMap) =====================

    @Test
    void thenCompose_flattensNestedFuture() throws Exception {
        // supplyAsync(42) → thenCompose(fetchUserName) → "User-42"
        String result = ops.thenComposeExample().get();
        assertEquals("User-42", result);
    }

    @Test
    void thenCompose_vs_thenApply_nesting() {
        // thenApply with a function that returns CF would give CF<CF<String>> — bad!
        CompletableFuture<CompletableFuture<String>> nested =
                CompletableFuture.supplyAsync(() -> 1)
                        .thenApply(id -> CompletableFuture.supplyAsync(() -> "User-" + id));
        // thenCompose flattens it to CF<String> — good!
        CompletableFuture<String> flat =
                CompletableFuture.supplyAsync(() -> 1)
                        .thenCompose(id -> CompletableFuture.supplyAsync(() -> "User-" + id));

        assertInstanceOf(CompletableFuture.class, nested.join()); // nested: result is another CF
        assertInstanceOf(String.class, flat.join());              // flat: result is String directly
    }

    // ===================== 5. thenCombine =====================

    @Test
    void thenCombine_mergesTwoFutures() throws Exception {
        String result = ops.thenCombineExample().get();
        assertEquals("Hello World", result);
    }

    @Test
    void thenCombine_computesFinalPrice() throws Exception {
        double result = ops.thenCombinePriceExample().get();
        assertEquals(85.0, result, 0.01); // 100 * (1 - 0.15)
    }

    // ===================== 6. applyToEither =====================

    @Test
    void applyToEither_returnsFirstToComplete() throws Exception {
        String result = ops.applyToEitherExample().get();
        assertEquals("Winner: fast-server", result);
    }

    // ===================== 7. allOf =====================

    @Test
    void allOf_waitsForAllAndCollectsResults() throws Exception {
        List<String> results = ops.allOfExample().get();
        assertEquals(List.of("A", "B", "C"), results);
    }

    @Test
    void allOf_failsIfAnyFutureFailsWhenJoined() {
        CompletableFuture<String> good = CompletableFuture.completedFuture("ok");
        CompletableFuture<String> bad = CompletableFuture.failedFuture(new RuntimeException("fail"));

        CompletableFuture<Void> all = CompletableFuture.allOf(good, bad);

        // allOf itself completes exceptionally if any child fails
        assertTrue(all.isCompletedExceptionally());
    }

    // ===================== 8. anyOf =====================

    @Test
    void anyOf_returnsFirstCompleted() throws Exception {
        Object result = ops.anyOfExample().get();
        assertEquals("Fast", result); // 50ms is the fastest
    }

    // ===================== 9. Error Handling =====================

    @Test
    void exceptionally_recoversFromError() throws Exception {
        String result = ops.exceptionallyExample(true).get();
        assertTrue(result.contains("Recovered from"));
        assertTrue(result.contains("Boom!"));
    }

    @Test
    void exceptionally_passesThrough_onSuccess() throws Exception {
        String result = ops.exceptionallyExample(false).get();
        assertEquals("Success", result);
    }

    @Test
    void handle_processesSuccess() throws Exception {
        String result = ops.handleExample(false).get();
        assertEquals("Handled success: OK", result);
    }

    @Test
    void handle_processesFailure() throws Exception {
        String result = ops.handleExample(true).get();
        assertTrue(result.contains("Handled error"));
        assertTrue(result.contains("Oops!"));
    }

    @Test
    void whenComplete_doesNotChangeResult() throws Exception {
        String result = ops.whenCompleteExample().get();
        assertEquals("data", result); // whenComplete is for side-effects only
    }

    @Test
    void whenComplete_propagatesOriginalException() {
        CompletableFuture<String> cf = CompletableFuture
                .<String>supplyAsync(() -> { throw new RuntimeException("original"); })
                .whenComplete((r, ex) -> System.out.println("Logged: " + ex));

        // The future is still exceptionally completed — whenComplete doesn't swallow errors
        assertTrue(cf.isCompletedExceptionally());
        assertThrows(ExecutionException.class, cf::get);
    }

    // ===================== 10. Timeout =====================

    @Test
    void orTimeout_fallsBackOnTimeout() throws Exception {
        String result = ops.orTimeoutExample().get();
        assertTrue(result.contains("Timed out"), "Expected timeout message but got: " + result);
    }

    @Test
    void completeOnTimeout_usesDefaultValue() throws Exception {
        String result = ops.completeOnTimeoutExample().get();
        assertEquals("Default (timed out)", result);
    }

    // ===================== 11. Realistic pipeline =====================

    @Test
    void realisticPipeline_combinesMultipleStages() throws Exception {
        String result = ops.realisticPipeline().get();
        // Pipeline: Order#1001 total=$100 (EUR: €85.0) → toUpperCase
        assertTrue(result.contains("ORDER#1001"));
        assertTrue(result.contains("EUR"));
        assertTrue(result.contains("85.0"));
    }

    // ===================== Bonus: Understanding execution threads =====================

    @Test
    void thenApply_vs_thenApplyAsync_threadBehavior() throws Exception {
        String callerThread = Thread.currentThread().getName();

        // thenApply — may run on the completing thread or the caller thread
        CompletableFuture<String> sync = CompletableFuture.completedFuture("x")
                .thenApply(s -> Thread.currentThread().getName());

        // thenApplyAsync — always runs on a pool thread
        CompletableFuture<String> async = CompletableFuture.completedFuture("x")
                .thenApplyAsync(s -> Thread.currentThread().getName());

        String syncThread = sync.get();
        String asyncThread = async.get();

        // thenApply on an already-completed future runs on the caller thread
        assertEquals(callerThread, syncThread);
        // thenApplyAsync always runs on ForkJoinPool
        assertNotEquals(callerThread, asyncThread);
        assertTrue(asyncThread.contains("ForkJoinPool"));
    }
}

