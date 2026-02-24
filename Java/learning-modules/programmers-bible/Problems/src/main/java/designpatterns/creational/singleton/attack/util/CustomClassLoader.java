package designpatterns.creational.singleton.attack.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

    private final String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        return defineClass(name, classData, 0, classData.length);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }
        Class<?> clazz = findLoadedClass(name);
        if (clazz == null) {
            try {
                clazz = findClass(name); // This loads the class from given path
            } catch (ClassNotFoundException e) {
                // If not found, delegate to parent
                clazz = super.loadClass(name, resolve);
            }
        }
        if (resolve) {
            resolveClass(clazz);
        }
        return clazz;
    }

    private byte[] loadClassData(String className) {
        String fileName = classPath + "/" + className.replace('.', '/') + ".class";
        try {
            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace(); // Just for demo
            return null;
        }
    }
}
