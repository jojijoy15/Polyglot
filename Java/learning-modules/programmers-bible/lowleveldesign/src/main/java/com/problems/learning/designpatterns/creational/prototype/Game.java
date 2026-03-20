package com.problems.learning.designpatterns.creational.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {

    public static void main(String[] args) {
        List<Prototype<?>> prototypes = new ArrayList<>();
        prototypes.add(new GameCharacter("gamer 1", "23", "F"));
        prototypes.add(new MainCharacter("gamer 1", "23", "F", "$34.33"));

        List<?> clones = IntStream.range(0, 10)
                .boxed()
                .flatMap(number -> prototypes.stream())
                .map(e -> e.proto())
                .toList();

        System.out.println(clones);
        System.out.println("Number of prototypes: " + clones.size());


    }
}
