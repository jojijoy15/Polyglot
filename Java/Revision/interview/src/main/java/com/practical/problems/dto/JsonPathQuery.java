package com.practical.problems.dto;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPathQuery {

  private static final Logger logger = LoggerFactory.getLogger(JsonPathQuery.class);

  public Map<String, Object> getJsonElementsAsVariables(String json, List<String> fields) {
    var defaultProvider = new JacksonJsonNodeJsonProvider();
    Configuration jsonParseConfig = new Configuration.ConfigurationBuilder()
        .jsonProvider(defaultProvider)
        .mappingProvider(new JacksonMappingProvider())
        .options(Option.SUPPRESS_EXCEPTIONS)
        .build();
    final DocumentContext parsedJsonContext = JsonPath.using(jsonParseConfig).parse(json);
    Map<String, Object> result = new HashMap<>();
    for (var field: fields) {
      Object parsedValue = Try.of(() -> parsedJsonContext.read("$." + field, new TypeRef<>() {}))
          .onFailure(err -> logger.error("Field could not be parsed : {}", err))
          .getOrElse(() -> null);
      int lastIndex = field.lastIndexOf('.');
      String fieldName = field.substring( lastIndex == -1 ? 0 : lastIndex + 1);
      if(result.containsKey(fieldName)) {
        String deDuplicatedFieldName = deDuplicateFieldKey(field);
        result.put(deDuplicatedFieldName, parsedValue);
      } else {
        result.put(fieldName, parsedValue);
      }
    }
    return result;
  }

  private String deDuplicateFieldKey(String fullFieldName) {
    String name = Arrays.stream(fullFieldName.split("\\."))
        .map(e -> e.replaceAll("[\\[\\]*\\$\\.\\:\\d]", ""))
        .map(WordUtils::uncapitalize)
        .collect(Collectors.joining());
    return name;
  }
}
