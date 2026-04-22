package com.problems.learning.designpatterns.behavioral.visitor.real;


import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileOps {

    public static final Logger logger = Logger.getLogger(FileOps.class.getName());

    public static void main(String[] args) {
        String source = "C:\\SourceCode\\Learning\\MyRepos\\Polyglot\\Java\\learning-modules\\programmers-bible\\lowleveldesign\\src";
        try {
            Stream<Path> walk = Files.walk(Path.of(source),
                    Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS);
            walk.forEach(System.out::println);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Some Exception occurred ", ex);
        }
    }
}
