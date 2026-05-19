package com.problems.learning.jpms.reflection;

/**
 * This class is in an `opens` package — meaning frameworks (Spring, Jackson, Hibernate)
 * can use deep reflection (setAccessible(true)) on its private fields.
 *
 * Without `opens`, calling setAccessible(true) on a private field throws:
 *   java.lang.reflect.InaccessibleObjectException
 *
 * KEY DIFFERENCE:
 *   exports → compile-time + runtime access to PUBLIC types only
 *   opens   → runtime reflection access to ALL types (including private)
 */
public class ReflectionTarget {

    private final String secret = "JPMS protects me!";
    private final int hiddenValue = 42;

    public String getPublicInfo() {
        return "This is public. The secret is hidden.";
    }

    @Override
    public String toString() {
        return "ReflectionTarget{secret='" + secret + "', hiddenValue=" + hiddenValue + "}";
    }
}

