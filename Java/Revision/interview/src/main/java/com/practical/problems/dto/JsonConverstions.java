package com.practical.problems.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConverstions {

  private static final Logger logger = LoggerFactory.getLogger(JsonConverstions.class);
  private final static ObjectMapper mapper = new ObjectMapper();

  public <T> T convertJsonToGivenType(String json, Class<T> clazz) {
    return Try.of(() -> mapper.readValue(json, clazz))
        .onSuccess(success -> logger.debug("Json -> Value conversion was successful with result {}", success))
        .onFailure(JsonProcessingException.class, error -> logger.error("Conversion failed with error {}", error))
        .get();
  }

  public <T> String convertGivenTypeToJson(T value) {
    return Try.of(() -> mapper.writeValueAsString(value))
        .onSuccess(success -> logger.debug("Object -> Json conversion was successful with result {}", success))
        .onFailure(JsonProcessingException.class, error -> logger.error("Conversion failed with error {}", error))
        .get();
  }

  public <T> T convertJsonToTypeRef(String json, TypeReference<T> clazz) {
    return Try.of(() -> mapper.readValue(json, clazz))
        .onSuccess(success -> logger.debug("Json -> Value conversion was successful with result {}", success))
        .onFailure(JsonProcessingException.class, error -> logger.error("Conversion failed with error {}", error))
        .get();
  }

}
