package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

  private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

  public static Properties retrieveConfigFromClassPath(String path)  {
    Objects.requireNonNull(path);
    return readFromFile(path);
  }

  private static Properties readFromFile(String path) {
    ClassLoader cl = Configuration.class.getClassLoader();
    try (InputStream resourceAsStream = cl.getResourceAsStream(path)){
        Properties config = new Properties();
      config.load(resourceAsStream);
      resourceAsStream.close();
      logger.info("Config Read from file path: {} successful", path);
      return config;
    } catch (IOException exception) {
      logger.error("Exception occurred when reading file from path: {}", path);
      throw new UncheckedIOException(exception);
    } finally {
      logger.info("Config Read from file path: {} completed", path);
    }
  }

}
