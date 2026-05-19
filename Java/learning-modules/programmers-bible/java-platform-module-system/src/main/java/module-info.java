/**
 * JPMS Module Descriptor — the heart of the module system.
 *
 * This file lives at the ROOT of the source tree (src/main/java/).
 * It defines:
 *   - What this module exports (public API)
 *   - What it keeps internal (encapsulation)
 *   - What it opens for reflection (frameworks)
 *   - What services it provides and consumes (SPI)
 */
module jpms.learning {

    // ─── EXPORTS ────────────────────────────────────────────────
    // Only the `api` package is visible to other modules.
    // The `internal` package is encapsulated — no one outside can use it.
    exports com.problems.learning.jpms.api;
    exports com.problems.learning.jpms.guide;

    // ─── OPENS ──────────────────────────────────────────────────
    // Allow deep reflection (setAccessible) on this package.
    // Needed for frameworks like Spring, Jackson, Hibernate.
    opens com.problems.learning.jpms.reflection;

    // ─── SPI: SERVICE PROVIDER INTERFACE ────────────────────────
    // `uses`     = this module CONSUMES the GreetingService
    // `provides` = this module SUPPLIES implementations
    uses com.problems.learning.jpms.api.GreetingService;
    provides com.problems.learning.jpms.api.GreetingService
        with com.problems.learning.jpms.internal.EnglishGreeting,
             com.problems.learning.jpms.internal.SpanishGreeting;
}

