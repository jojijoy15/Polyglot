package com.problems.learning.jpms.api;

/**
 * SERVICE INTERFACE — the public API.
 * This package is EXPORTED in module-info.java.
 * Implementations live in the `internal` package which is NOT exported.
 */
public interface GreetingService {
    String greet(String name);
    String language();
}
