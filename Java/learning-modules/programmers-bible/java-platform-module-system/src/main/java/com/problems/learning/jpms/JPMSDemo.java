package com.problems.learning.jpms;

import com.problems.learning.jpms.api.GreetingService;
import com.problems.learning.jpms.reflection.ReflectionTarget;

import java.lang.module.ModuleDescriptor;
import java.lang.reflect.Field;
import java.util.ServiceLoader;

/**
 * RUNNABLE JPMS DEMO — demonstrates all key JPMS features.
 *
 * Run: mvn clean compile exec:java -Dexec.mainClass="com.problems.learning.jpms.JPMSDemo"
 */
public class JPMSDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║          JPMS (Java Module System) Demo      ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        demo1_ServiceLoader();
        demo2_Encapsulation();
        demo3_Reflection();
        demo4_ModuleInfo();
    }

    /**
     * DEMO 1: ServiceLoader (SPI) — `uses` and `provides...with`
     *
     * ServiceLoader discovers implementations at runtime without
     * the consumer knowing the concrete class names.
     *
     * In module-info.java:
     *   uses GreetingService;
     *   provides GreetingService with EnglishGreeting, SpanishGreeting;
     */
    private static void demo1_ServiceLoader() {
        System.out.println("\n═══ DEMO 1: ServiceLoader (SPI) ═══");
        System.out.println("Loading all GreetingService implementations...\n");

        ServiceLoader<GreetingService> loader = ServiceLoader.load(GreetingService.class);

        boolean found = false;
        for (GreetingService service : loader) {
            found = true;
            System.out.println("  [" + service.language() + "] " + service.greet("Developer"));
        }

        if (!found) {
            System.out.println("  ⚠ No providers found.");
            System.out.println("  → This happens when running on CLASSPATH (unnamed module).");
            System.out.println("  → ServiceLoader reads META-INF/services/ on classpath,");
            System.out.println("    or module-info.java `provides` on module path.");
            System.out.println("  → Falling back to manual instantiation for demo:");

            // Manual fallback to show the concept works
            try {
                Class<?> eng = Class.forName("com.problems.learning.jpms.internal.EnglishGreeting");
                Class<?> spa = Class.forName("com.problems.learning.jpms.internal.SpanishGreeting");
                GreetingService english = (GreetingService) eng.getDeclaredConstructor().newInstance();
                GreetingService spanish = (GreetingService) spa.getDeclaredConstructor().newInstance();
                System.out.println("  [" + english.language() + "] " + english.greet("Developer"));
                System.out.println("  [" + spanish.language() + "] " + spanish.greet("Developer"));
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    /**
     * DEMO 2: Encapsulation — `exports` vs non-exported
     *
     * - `api` package is exported       → other modules CAN access GreetingService
     * - `internal` package is NOT exported → other modules CANNOT access EnglishGreeting
     *
     * Within the SAME module, all packages are accessible (no restriction).
     * The encapsulation only applies ACROSS module boundaries.
     */
    private static void demo2_Encapsulation() {
        System.out.println("\n═══ DEMO 2: Encapsulation (exports) ═══");

        // Within our own module, we CAN access internal classes directly
        var english = new com.problems.learning.jpms.internal.EnglishGreeting();
        System.out.println("  Within same module, internal access works: " + english.greet("JPMS"));

        System.out.println();
        System.out.println("  Key points:");
        System.out.println("  ┌───────────────────────────────────────────────────────┐");
        System.out.println("  │ exports com.problems.learning.jpms.api;               │");
        System.out.println("  │   → Other modules CAN use GreetingService interface   │");
        System.out.println("  │                                                       │");
        System.out.println("  │ internal package is NOT exported:                      │");
        System.out.println("  │   → Other modules CANNOT use EnglishGreeting directly │");
        System.out.println("  │   → They must use ServiceLoader to discover it        │");
        System.out.println("  └───────────────────────────────────────────────────────┘");
    }

    /**
     * DEMO 3: Reflection — `opens` keyword
     *
     * The `opens com.problems.learning.jpms.reflection` directive allows
     * frameworks to use deep reflection (setAccessible(true)) on private fields.
     *
     * Without `opens`, this would throw InaccessibleObjectException.
     */
    private static void demo3_Reflection() throws Exception {
        System.out.println("\n═══ DEMO 3: Reflection (opens) ═══");

        ReflectionTarget target = new ReflectionTarget();
        System.out.println("  Public access: " + target.getPublicInfo());

        // Deep reflection on private field — works because package is `opens`
        Field secretField = ReflectionTarget.class.getDeclaredField("secret");
        secretField.setAccessible(true); // Would FAIL without `opens`!
        String secretValue = (String) secretField.get(target);
        System.out.println("  Reflected private 'secret': " + secretValue);

        Field hiddenField = ReflectionTarget.class.getDeclaredField("hiddenValue");
        hiddenField.setAccessible(true);
        int hiddenValue = (int) hiddenField.get(target);
        System.out.println("  Reflected private 'hiddenValue': " + hiddenValue);

        System.out.println();
        System.out.println("  Key points:");
        System.out.println("  ┌──────────────────────────────────────────────────────┐");
        System.out.println("  │ opens com.problems.learning.jpms.reflection;         │");
        System.out.println("  │   → Allows setAccessible(true) on private members    │");
        System.out.println("  │   → Required by Spring, Jackson, Hibernate, etc.     │");
        System.out.println("  │                                                      │");
        System.out.println("  │ exports ≠ opens:                                     │");
        System.out.println("  │   exports = compile-time access to public types      │");
        System.out.println("  │   opens   = runtime reflection on ALL (inc. private) │");
        System.out.println("  └──────────────────────────────────────────────────────┘");
    }

    /**
     * DEMO 4: Module introspection at runtime
     */
    private static void demo4_ModuleInfo() {
        System.out.println("\n═══ DEMO 4: Module Info at Runtime ═══");

        Module module = JPMSDemo.class.getModule();
        System.out.println("  Module name  : " + module.getName());
        System.out.println("  Is named     : " + module.isNamed());
        System.out.println("  Class loader : " + module.getClassLoader());

        if (module.isNamed()) {
            ModuleDescriptor desc = module.getDescriptor();
            System.out.println("  Exports      : " + desc.exports());
            System.out.println("  Opens        : " + desc.opens());
            System.out.println("  Requires     : " + desc.requires());
            System.out.println("  Provides     : " + desc.provides());
            System.out.println("  Uses         : " + desc.uses());
        } else {
            System.out.println("  → Running on CLASSPATH (unnamed module).");
            System.out.println("  → To run on MODULE PATH, use:");
            System.out.println("    java --module-path target/classes -m jpms.learning/com.problems.learning.jpms.JPMSDemo");
        }

        System.out.println("\n═══ DEMO COMPLETE ═══");
    }
}

