package com.problems.learning.designpatterns.creational.singleton.impl;

import java.util.logging.Logger;

/*
    Synchronized Method Singleton (Lazy + Thread-Safe)

    The simplest thread-safe lazy singleton — synchronizes the entire getInstance() method.

    Avoids following attacks:
        1. Concurrency Attack — synchronized guarantees one thread at a time

    Permits following attacks:
        1. Reflection Attack — constructor can be called via reflection
        2. Serialization Attack — if made Serializable, deserialization creates new instance
        3. Multiple Classloader Attack — different classloaders can load separate instances

    Drawback: EVERY call to getInstance() acquires the lock, even after the instance
    is already created. This is unnecessary overhead — the lock is only needed during
    the first creation. That's why DoubleCheckedLockingSingleton is preferred.

    HOW TO BREAK:
        // Reflection Attack
        Constructor<SynchronizedSingleton> c = SynchronizedSingleton.class.getDeclaredConstructor();
        c.setAccessible(true);
        SynchronizedSingleton broken = c.newInstance(); // NEW instance — singleton broken!
*/
public class SynchronizedSingleton {

    private static final Logger logger = Logger.getLogger(SynchronizedSingleton.class.getName());
    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
    }

    public static synchronized SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        logger.info("Retrieving instance of SynchronizedSingleton");
        return instance;
    }
}

