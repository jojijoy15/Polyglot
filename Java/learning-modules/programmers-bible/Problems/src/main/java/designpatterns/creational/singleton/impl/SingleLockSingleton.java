package designpatterns.creational.singleton.impl;

import java.util.logging.Logger;

/*
    Permits following attacks
        1. If made serializable, cannot prevent serialization attack
        2. Multiple Classloader Attack
        3. Reflection Attack

    Avoids following attacks
        1. Concurrency Attack
*/
public class SingleLockSingleton {

    private static final Logger logger = Logger.getLogger(SingleLockSingleton.class.getName());
    private static SingleLockSingleton instance;

    private SingleLockSingleton() {}

    public static SingleLockSingleton getInstance() {
        logger.info("Retrieving a new instance of SingleLockSingleton");
        if(instance == null) {
            synchronized (SingleLockSingleton.class) {
                instance = new SingleLockSingleton();
            }
        }
        return instance;
    }
}
