package com.revision.utils;

import io.vavr.control.Try;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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

  public static Map<String, String> csvReader(String path) {
    Map<String, String> csvWords = new HashMap<>();
    Try.of(()-> {
       Reader reader = new FileReader(path);
       Iterable<CSVRecord> records = CSVFormat.newFormat(',').parse(reader);
       for (CSVRecord record : records) {
         csvWords.put( record.get(0), record.get(1));
       }
       return csvWords;
    })
    .onSuccess(response -> logger.info("csv read completed successfully"))
    .onFailure(error -> {
      logger.error("The csv File could not be read {}", error);
      throw new RuntimeException(error.getMessage());
    });
    return Collections.emptyMap();
  }

  public static String getFileContents(String path) {
    Path filePath = Paths.get(path);
    return Try.of(() -> Files.readString(filePath, Charset.defaultCharset()))
        .getOrElseThrow(error -> {
              logger.error("The file could not be read {}", error);
              return new RuntimeException(error.getMessage());
            }
        );
  }
}
