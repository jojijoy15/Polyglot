package designpatterns.creational.singleton.impl;

/*
    Permits following attacks
        1. Multiple Classloader Attack

    Avoids following attacks
        1. Concurrency Attack due to java guarantees
        2. If made serializable, cannot prevent serialization attack
        3. Reflection Attack

*/
public enum EnumSingleton {

    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
