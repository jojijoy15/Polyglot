package com.problems.learning.designpatterns.creational.singleton.impl;

import java.util.logging.Logger;

/*
    Correct Double-Checked Locking Singleton (Lazy + Thread-Safe + Performant)

    Fixes the issues in DoubleCheckSingleLockSingleton:
      1. Uses 'volatile' to prevent instruction reordering
      2. Actually performs DOUBLE check (check → lock → check again → create)

    Why volatile?
        Without volatile, the JVM can reorder:
            instance = new Singleton();
        into:
            1. Allocate memory
            2. Assign reference to instance  ← other thread sees non-null here!
            3. Run constructor               ← but object is NOT fully constructed
        volatile prevents this reordering (happens-before guarantee).

    Avoids following attacks:
        1. Concurrency Attack — volatile + double-check guarantees single creation

    Permits following attacks:
        1. Reflection Attack — constructor can be called via reflection
        2. Serialization Attack — if made Serializable, deserialization creates new instance
        3. Multiple Classloader Attack

    HOW TO BREAK:
        // Reflection Attack
        Constructor<DoubleCheckedLockingSingleton> c =
            DoubleCheckedLockingSingleton.class.getDeclaredConstructor();
        c.setAccessible(true);
        DoubleCheckedLockingSingleton broken = c.newInstance(); // NEW instance!
*/
public class DoubleCheckedLockSingleton {

    private static final Logger logger = Logger.getLogger(DoubleCheckedLockSingleton.class.getName());

    // volatile is CRITICAL — prevents instruction reordering
    private static volatile DoubleCheckedLockSingleton instance;

    private DoubleCheckedLockSingleton() {
    }

    public static DoubleCheckedLockSingleton getInstance() {
        if (instance == null) {                                    // 1st check (no lock)
            synchronized (DoubleCheckedLockSingleton.class) {
                if (instance == null) {                            // 2nd check (with lock)
                    instance = new DoubleCheckedLockSingleton();
                }
            }
        }
        logger.info("Retrieving instance of DoubleCheckedLockingSingleton");
        return instance;
    }
}

