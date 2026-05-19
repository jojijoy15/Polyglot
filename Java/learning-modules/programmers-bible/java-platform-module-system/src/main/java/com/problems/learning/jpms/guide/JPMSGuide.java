package com.problems.learning.jpms.guide;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║           JAVA PLATFORM MODULE SYSTEM (JPMS) - COMPLETE GUIDE       ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * Introduced in Java 9 (Project Jigsaw), JPMS adds a higher level of
 * aggregation above packages — the MODULE.
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  BEFORE JPMS (Java 1-8)                                            │
 * │                                                                     │
 * │  - Everything on the classpath is a flat, global namespace          │
 * │  - Any public class can be accessed by any other class              │
 * │  - No way to hide internal APIs (e.g., sun.misc.Unsafe)            │
 * │  - JAR Hell: duplicate classes, version conflicts                   │
 * │  - JRE is monolithic (~200 MB even for a "Hello World")             │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  AFTER JPMS (Java 9+)                                               │
 * │                                                                     │
 * │  - Strong encapsulation: only exported packages are accessible      │
 * │  - Reliable configuration: explicit dependencies via `requires`     │
 * │  - Smaller runtimes: jlink creates custom JRE with only needed      │
 * │    modules                                                          │
 * │  - No more JAR Hell: split packages are detected at startup         │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * ═══════════════════════════════════════════════════════════════════════
 * KEY CONCEPTS
 * ═══════════════════════════════════════════════════════════════════════
 *
 * 1. MODULE = A named, self-describing collection of packages
 *    - Defined by `module-info.java` at the root of the source tree
 *    - Compiled to `module-info.class` in the JAR root
 *
 * 2. MODULE DESCRIPTOR (module-info.java) keywords:
 *
 *    ┌──────────────────────┬────────────────────────────────────────────┐
 *    │ Keyword              │ Purpose                                    │
 *    ├──────────────────────┼────────────────────────────────────────────┤
 *    │ module               │ Declares a module                          │
 *    │ requires             │ Declares dependency on another module      │
 *    │ requires transitive  │ Dependency is passed to downstream modules │
 *    │ exports              │ Makes a package public to other modules    │
 *    │ exports ... to       │ Makes a package public to SPECIFIC modules │
 *    │ opens                │ Allows deep reflection on a package        │
 *    │ opens ... to         │ Allows reflection from SPECIFIC modules    │
 *    │ uses                 │ Declares this module consumes a service    │
 *    │ provides ... with    │ Declares this module provides a service    │
 *    └──────────────────────┴────────────────────────────────────────────┘
 *
 * 3. MODULE TYPES:
 *
 *    a) Named Module     → Has module-info.java, lives on module path
 *    b) Automatic Module → A regular JAR placed on module path (no module-info).
 *                          Module name = JAR filename or Automatic-Module-Name manifest.
 *    c) Unnamed Module   → Everything on the classpath. Can read all modules
 *                          but no module can read it.
 *
 * 4. ACCESSIBILITY RULES:
 *
 *    ┌────────────────────────┬────────────────┬────────────────────────┐
 *    │                        │ Exported Pkg   │ Non-exported Pkg       │
 *    ├────────────────────────┼────────────────┼────────────────────────┤
 *    │ Public type            │ ✅ Accessible  │ ❌ Inaccessible        │
 *    │ Public method          │ ✅ Accessible  │ ❌ Inaccessible        │
 *    │ Reflection (setAcc.)   │ ❌ Denied*     │ ❌ Denied*             │
 *    │ Reflection (opened)    │ ✅ Allowed     │ ✅ Allowed             │
 *    └────────────────────────┴────────────────┴────────────────────────┘
 *    * Unless the package is `opens`
 *
 * ═══════════════════════════════════════════════════════════════════════
 * EXAMPLE module-info.java (this project):
 * ═══════════════════════════════════════════════════════════════════════
 *
 *   module jpms.learning {
 *       // --- EXPORTS: Make packages visible to other modules ---
 *       exports com.problems.learning.jpms.api;
 *       // com.problems.learning.jpms.internal is NOT exported → encapsulated!
 *
 *       // --- OPENS: Allow deep reflection (frameworks like Spring, Jackson) ---
 *       opens com.problems.learning.jpms.reflection;
 *
 *       // --- SPI: Service Provider Interface ---
 *       uses com.problems.learning.jpms.api.GreetingService;
 *       provides com.problems.learning.jpms.api.GreetingService
 *           with com.problems.learning.jpms.internal.EnglishGreeting,
 *                com.problems.learning.jpms.internal.SpanishGreeting;
 *   }
 *
 * ═══════════════════════════════════════════════════════════════════════
 * THIS PROJECT DEMONSTRATES:
 * ═══════════════════════════════════════════════════════════════════════
 *
 *   1. EXPORTS vs NON-EXPORTED packages
 *      - api package     → exported (public API)
 *      - internal package → NOT exported (hidden implementation)
 *
 *   2. SERVICE PROVIDER INTERFACE (SPI) with ServiceLoader
 *      - `uses` declares the service interface
 *      - `provides...with` declares implementations
 *      - ServiceLoader.load() discovers implementations at runtime
 *
 *   3. OPENS for reflection
 *      - reflection package is `opens` so frameworks can reflectively access it
 *
 *   4. REQUIRES
 *      - We don't require external modules (self-contained demo)
 *      - Every module implicitly `requires java.base`
 *
 * ═══════════════════════════════════════════════════════════════════════
 * HOW TO RUN:
 * ═══════════════════════════════════════════════════════════════════════
 *
 *   From the java-platform-module-system directory:
 *
 *   1. Build:  mvn clean compile
 *   2. Run:    mvn exec:java -Dexec.mainClass="com.problems.learning.jpms.JPMSDemo"
 *
 *   Or manually:
 *   javac -d out $(find src -name "*.java")
 *   java --module-path out -m jpms.learning/com.problems.learning.jpms.JPMSDemo
 *
 * ═══════════════════════════════════════════════════════════════════════
 * COMMON JPMS COMMANDS:
 * ═══════════════════════════════════════════════════════════════════════
 *
 *   java --list-modules                    → List all platform modules
 *   java --describe-module java.sql        → Describe a module
 *   java --show-module-resolution -version → Show how modules are resolved
 *   jar --describe-module --file=app.jar   → Describe module in a JAR
 *   jdeps --module-path mods -s app.jar    → Analyze dependencies
 *   jlink --module-path ... --add-modules  → Create custom runtime image
 *
 * ═══════════════════════════════════════════════════════════════════════
 * JPMS vs OSGi vs CLASSPATH
 * ═══════════════════════════════════════════════════════════════════════
 *
 *   ┌────────────┬────────────┬──────────────────────┬──────────────────┐
 *   │ Feature    │ Classpath  │ JPMS                 │ OSGi             │
 *   ├────────────┼────────────┼──────────────────────┼──────────────────┤
 *   │ Encapsulat.│ ❌ None    │ ✅ Compile+Runtime   │ ✅ Runtime only  │
 *   │ Versioning │ ❌ No      │ ❌ No                │ ✅ Yes           │
 *   │ Dynamic    │ ❌ No      │ ❌ No (static)       │ ✅ Yes (hot swap)│
 *   │ JDK Built  │ ✅ Yes     │ ✅ Yes (since J9)    │ ❌ External      │
 *   │ Service    │ META-INF   │ provides/uses        │ DS/Blueprint     │
 *   └────────────┴────────────┴──────────────────────┴──────────────────┘
 */
public class JPMSGuide {
    // This class serves as documentation. See JPMSDemo.java for runnable code.
}

