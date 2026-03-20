package com.problems.learning.designpatterns.creational.singleton.client;

import com.problems.learning.designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import com.problems.learning.designpatterns.creational.singleton.attack.impl.eager.ClassLoaderAttack;
import com.problems.learning.designpatterns.creational.singleton.attack.impl.eager.ConcurrencyAttack;
import com.problems.learning.designpatterns.creational.singleton.attack.impl.eager.ReflectionAttack;
import com.problems.learning.designpatterns.creational.singleton.attack.impl.eager.SerializationAttack;

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
            new com.problems.learning.designpatterns.creational.singleton.attack.impl.enumeration.SerializationAttack(),
            new com.problems.learning.designpatterns.creational.singleton.attack.impl.enumeration.ConcurrencyAttack(),
            new com.problems.learning.designpatterns.creational.singleton.attack.impl.enumeration.ClassLoaderAttack(),
            new com.problems.learning.designpatterns.creational.singleton.attack.impl.enumeration.ReflectionAttack()
        );

        enumAttacks.forEach(e -> {
            logger.info("\r\n#### Performing " + e.getClass() + " #####");
            e.breakSingleton();
            logger.info("#### Completed " + e.getClass() + " #####\r\n");
        });
    }
}
