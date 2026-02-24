package designpatterns.creational.singleton.client;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.attack.impl.eager.ClassLoaderAttack;
import designpatterns.creational.singleton.attack.impl.eager.ConcurrencyAttack;
import designpatterns.creational.singleton.attack.impl.eager.ReflectionAttack;
import designpatterns.creational.singleton.attack.impl.eager.SerializationAttack;

import java.util.List;
import java.util.logging.Logger;

public class Driver {

    private static final Logger logger = Logger.getLogger(Driver.class.getName());

    public static void main(String[] args) {

        List<FakeSingletonDetectorAttack> classAttacks = List.of(
            new ReflectionAttack(),
            new SerializationAttack(),
            new ConcurrencyAttack(),
            new ClassLoaderAttack()
        );
        classAttacks.forEach(e -> {
            logger.info("\r\n#### Performing " + e.getClass() + " #####");
            e.breakSingleton();
            logger.info("#### Completed " + e.getClass() + " #####\r\n");
        });

        logger.info("\r\n#### Performing Attacks on Enum Singleton #####\r\n");

        List<FakeSingletonDetectorAttack> enumAttacks = List.of(
            new designpatterns.creational.singleton.attack.impl.enumeration.SerializationAttack(),
            new designpatterns.creational.singleton.attack.impl.enumeration.ConcurrencyAttack(),
            new designpatterns.creational.singleton.attack.impl.enumeration.ClassLoaderAttack(),
            new designpatterns.creational.singleton.attack.impl.enumeration.ReflectionAttack()
        );

        enumAttacks.forEach(e -> {
            logger.info("\r\n#### Performing " + e.getClass() + " #####");
            e.breakSingleton();
            logger.info("#### Completed " + e.getClass() + " #####\r\n");
        });
    }
}
