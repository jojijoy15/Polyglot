package designpatterns.creational.singleton.attack.impl.eager;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.attack.util.CustomClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ClassLoaderAttack implements FakeSingletonDetectorAttack {

    private static final Logger logger = Logger.getLogger(ClassLoaderAttack.class.getName());
    private static final String classPath = "C:\\SourceCode\\Learning\\Java\\Language\\fundamentals\\target\\classes";
    public static final String fqcn = "org.practice.designpatterns.creational.singleton.impl.EagerSingleton";

    @Override
    public void breakSingleton() {
        try {
            ClassLoader classLoader = new CustomClassLoader(classPath);
            Class<?> aClass = classLoader.loadClass(fqcn);
            Method declaredMethod = aClass.getDeclaredMethod("getInstance");
            Object firstInstance = declaredMethod.invoke(aClass);
            Object secondInstance = declaredMethod.invoke(aClass);
            logger.info("ClassLoader Attack :: Are both instances loaded via custom class loader same? : "
                    + (firstInstance == secondInstance));

            //Second Class Loader
            ClassLoader anotherClassLoader = new CustomClassLoader(classPath);
            Class<?> anotherClass = anotherClassLoader.loadClass(fqcn);
            Method anotherClassLoaderdeclaredMethod = anotherClass.getDeclaredMethod("getInstance");
            Object newSingleInstance = anotherClassLoaderdeclaredMethod.invoke(anotherClass);
            logger.info("ClassLoader Attack :: Are both instances loaded via Existing class loader same? : "
                    + (newSingleInstance == firstInstance));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace(); // Just for demo
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
