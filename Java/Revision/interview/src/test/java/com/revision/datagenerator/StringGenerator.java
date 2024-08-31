package com.revision.datagenerator;

import com.revision.datagenerator.configurations.FakerConfiguration;
import java.util.List;

public class StringGenerator {

  public static List<String> generateLorenIpsumWords(int limit) {
    var faker = FakerConfiguration.createSimpeFaker();
    return faker.lorem().words(limit).stream().toList();
  }

  public static String generateLoremIpsumWord() {
    return generateLorenIpsumWords(1).get(1);
  }


}
