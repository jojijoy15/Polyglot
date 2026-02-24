package designpatterns.creational.singleton.attack.impl.eager;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.EagerSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class ReflectionAttack implements FakeSingletonDetectorAttack {

    private static final Logger logger = Logger.getLogger(ReflectionAttack.class.getName());

    @Override
    public void breakSingleton() {
        EagerSingleton firstInstance = EagerSingleton.getInstance();
        try {
            Constructor<?> constructor = EagerSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            EagerSingleton secondInstance = (EagerSingleton) constructor.newInstance();
            logger.info("Reflection Attack :: Are both instances same? : "
                    + (firstInstance == secondInstance));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }
}
