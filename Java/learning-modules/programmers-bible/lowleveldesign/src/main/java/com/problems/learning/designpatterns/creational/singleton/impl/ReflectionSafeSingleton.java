package com.problems.learning.designpatterns.creational.singleton.impl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/*
    Reflection-Safe Singleton (The most defensive non-enum singleton)

    Prevents ALL common attacks:
        1. Concurrency Attack  — eager init (class loading guarantee)
        2. Serialization Attack — readResolve() returns existing instance
        3. Reflection Attack    — constructor throws if instance already exists

    Still permits:
        1. Multiple Classloader Attack — if two classloaders load this class,
           each gets its own static field, so two instances exist in different contexts.
           (Only Enum can partially defend against this)

    HOW TO BREAK:
        Reflection → IllegalStateException thrown from constructor
        Serialization → readResolve() returns existing instance
        Concurrency → eager init handles it

        The ONLY way to break this is via Multiple Classloaders:
            URLClassLoader cl = new URLClassLoader(new URL[]{...}, null);
            Class<?> clazz = cl.loadClass("...ReflectionSafeSingleton");
            // This loads the class again → new static field → new instance
*/
public class ReflectionSafeSingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ReflectionSafeSingleton.class.getName());
    private static final ReflectionSafeSingleton instance = new ReflectionSafeSingleton();

    private ReflectionSafeSingleton() {
        // Defend against reflection: if instance already exists, block construction
        if (instance != null) {
            throw new IllegalStateException(
                "Singleton already constructed. Use getInstance(). Reflection attack blocked!"
            );
        }
    }

    public static ReflectionSafeSingleton getInstance() {
        logger.info("Retrieving instance of ReflectionSafeSingleton");
        return instance;
    }

    @Serial
    private Object readResolve() {
        return instance;
    }
}

