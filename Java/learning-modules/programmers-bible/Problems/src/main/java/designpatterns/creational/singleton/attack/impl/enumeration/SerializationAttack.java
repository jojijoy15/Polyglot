package designpatterns.creational.singleton.attack.impl.enumeration;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.EnumSingleton;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializationAttack implements FakeSingletonDetectorAttack {

    private static final Logger logger = Logger.getLogger(SerializationAttack.class.getName());

    @Override
    public void breakSingleton() {
        EnumSingleton singleton = EnumSingleton.getInstance();
        writeObject(singleton);
        EnumSingleton anotherInstance = readObject();
        logger.info("Serialization Attack :: Are both instances same? : "
            +  (singleton == anotherInstance));
    }

    private void writeObject(Object object) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("target\\binary.ser"))) {
            os.writeObject(object);
        } catch(IOException exception) {
            logger.severe(exception.getMessage());
        }
    }

    private EnumSingleton readObject() {
        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream("target\\binary.ser"))) {
            return (EnumSingleton) os.readObject();
        } catch(IOException | ClassNotFoundException ex) {
            logger.setLevel(Level.SEVERE);
            logger.severe(ex.getMessage());
        }
        return null;
    }
}
