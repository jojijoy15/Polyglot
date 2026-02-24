package designpatterns.creational.singleton.impl;

import java.io.Serializable;

/*
    Permits following attacks
        1. If made serializable, cannot prevent serialization attack
        2. Multiple Classloader Attack
        3. Reflection Attack

    Avoids following attacks
        1. Concurrency Attack due to java guarantees
*/
public class SerializableEagerSingleton implements Serializable {

    private static final SerializableEagerSingleton instance = new SerializableEagerSingleton();

    private SerializableEagerSingleton() {}

    public static SerializableEagerSingleton getInstance() {
        return instance;
    }
}
