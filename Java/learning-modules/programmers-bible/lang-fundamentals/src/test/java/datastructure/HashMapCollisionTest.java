package datastructure;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.stream.IntStream;

public class HashMapCollisionTest {

    public static void main(String[] args) {

        HashMap<Person, Integer> map = new HashMap<>();
        IntStream.range(0, 65)
                .peek(id -> {
                    map.put(new Person(id), id);
                })
                .forEach(id -> {
                    printMapStatistics(map);
                });
    }

    private static void printMapStatistics(HashMap<Person, Integer> map) {
        try {
            Field table = map.getClass().getDeclaredField("table");
            table.setAccessible(true);
            Object bucket = table.get(map);
            Field size = map.getClass().getDeclaredField("size");
            size.setAccessible(true);
            Field threshold = map.getClass().getDeclaredField("threshold");
            threshold.setAccessible(true);
            System.out.println("bucket: " + java.lang.reflect.Array.getLength(bucket) +
                    " size: " + size.getInt(map) +
                    ", threshold: " +  threshold.getInt(map));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static class Person {
        int id;
        Person(int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return 1;   // Did to create hashCollision
        }

    }

}
