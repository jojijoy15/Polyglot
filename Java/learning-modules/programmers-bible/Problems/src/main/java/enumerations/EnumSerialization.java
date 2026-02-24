package enumerations;

import java.io.*;

public class EnumSerialization{

    public static void main(String[] args) {

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("target\\enum.ser"))) {
            ProductType UTENSILS = ProductType.valueOf("UTENSILS");
            os.writeObject(UTENSILS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream("target\\enum.ser"))) {
            ProductType UTENSILS = (ProductType) os.readObject();
            System.out.println(UTENSILS);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
