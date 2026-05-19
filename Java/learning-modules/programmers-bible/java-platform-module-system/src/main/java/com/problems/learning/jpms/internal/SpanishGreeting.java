package com.problems.learning.jpms.internal;

import com.problems.learning.jpms.api.GreetingService;

/**
 * Another INTERNAL implementation — NOT exported.
 * Discovered only via ServiceLoader.
 */
public class SpanishGreeting implements GreetingService {

    @Override
    public String greet(String name) {
        return "¡Hola, " + name + "!";
    }

    @Override
    public String language() {
        return "Spanish";
    }
}

