package designpatterns.creational.singleton.attack.impl.enumeration;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.EnumSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class ReflectionAttack implements FakeSingletonDetectorAttack {

    private static final Logger logger = Logger.getLogger(ReflectionAttack.class.getName());

    @Override
    public void breakSingleton() {
        EnumSingleton firstInstance = EnumSingleton.getInstance();
        try {
            Constructor<?> constructor = EnumSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            EnumSingleton secondInstance = (EnumSingleton) constructor.newInstance();
            logger.info("Reflection Attack :: Are both instances same? : "
                    + (firstInstance == secondInstance));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }
}
