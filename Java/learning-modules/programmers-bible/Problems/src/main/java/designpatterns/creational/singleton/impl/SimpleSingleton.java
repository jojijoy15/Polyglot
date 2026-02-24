package designpatterns.creational.singleton.impl;

import java.util.logging.Logger;

/*
    Permits following attacks
        1. If made serializable, cannot prevent serialization attack
        2. Multiple Classloader Attack
        3. Reflection Attack
        4. Concurrency Attack
*/
public class SimpleSingleton {

    private static final Logger logger = Logger.getLogger(SimpleSingleton.class.getName());

    private static SimpleSingleton instance ;

    private SimpleSingleton() {}

    public static SimpleSingleton getInstance() /*throws InterruptedException*/ {
        if (instance == null) {
            /* Uncomment when performing concurrent attack to simulate artificial delay */
            // Thread.sleep(new Random().nextInt(1000, 5000));
            instance = new SimpleSingleton();
        }
        logger.info("Retrieving a new instance of SimpleSingleton");
        return instance;
    }

}
