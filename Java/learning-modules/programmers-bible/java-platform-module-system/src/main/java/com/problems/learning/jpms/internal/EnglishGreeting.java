package com.problems.learning.jpms.internal;

import com.problems.learning.jpms.api.GreetingService;

/**
 * INTERNAL implementation — this package is NOT exported.
 * Other modules CANNOT directly instantiate this class.
 * They can only discover it via ServiceLoader (SPI).
 */
public class EnglishGreeting implements GreetingService {

    @Override
    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    @Override
    public String language() {
        return "English";
    }
}

