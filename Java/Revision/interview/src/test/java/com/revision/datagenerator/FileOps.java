package com.revision.datagenerator;

import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOps {

  public static final Logger logger = LoggerFactory.getLogger(FileOps.class);

  public static List<String> readFileLines(String path) {
    Path filePath = Paths.get(path);
    return Try.of(() -> Files.readAllLines(filePath))
        .getOrElseThrow(error -> {
            logger.error("The file could not be read {}", error);
            return new RuntimeException(error.getMessage());
          }
        );
  }
}
