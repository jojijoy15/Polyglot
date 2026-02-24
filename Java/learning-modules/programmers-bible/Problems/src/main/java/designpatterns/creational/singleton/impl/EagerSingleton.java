package designpatterns.creational.singleton.impl;

import java.util.logging.Logger;

/*
    Permits following attacks
        1. If made serializable, cannot prevent serialization attack
        2. Multiple Classloader Attack
        3. Reflection Attack

    Avoids following attacks
        1. Concurrency Attack due to java guarantees
*/
public class EagerSingleton {

    private static final Logger logger = Logger.getLogger(EagerSingleton.class.getName());
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        logger.info("Retrieving a new instance of EagerSingleton");
        return instance;
    }

}
