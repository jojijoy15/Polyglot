package com.problems.learning.designpatterns.creational.singleton.impl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/*
    Serialization-Safe Singleton

    Prevents the Serialization Attack by implementing readResolve().

    The Problem:
        When you deserialize an object, Java creates a NEW instance bypassing the constructor.
        So even if getInstance() returns the same object, deserialization creates a second one.

    The Fix:
        readResolve() is a special method called by the deserialization mechanism.
        If present, the object returned by readResolve() REPLACES the deserialized object.
        So we return the existing singleton instance, and the deserialized copy is discarded.

    Avoids following attacks:
        1. Concurrency Attack — eager initialization
        2. Serialization Attack — readResolve() returns existing instance

    Permits following attacks:
        1. Reflection Attack — constructor can still be called via reflection
        2. Multiple Classloader Attack

    HOW TO BREAK:
        // Reflection Attack (serialization is defended, but reflection is not)
        Constructor<SerializationSafeSingleton> c =
            SerializationSafeSingleton.class.getDeclaredConstructor();
        c.setAccessible(true);
        SerializationSafeSingleton broken = c.newInstance(); // different instance!
*/
public class SerializationSafeSingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SerializationSafeSingleton.class.getName());
    private static final SerializationSafeSingleton instance = new SerializationSafeSingleton();

    private SerializationSafeSingleton() {
    }

    public static SerializationSafeSingleton getInstance() {
        logger.info("Retrieving instance of SerializationSafeSingleton");
        return instance;
    }

    /*
       This method is called during deserialization.
       Instead of returning the newly deserialized object,
       it returns the existing singleton — preventing a second instance.
    */
    @Serial
    private Object readResolve() {
        return instance;
    }
}

