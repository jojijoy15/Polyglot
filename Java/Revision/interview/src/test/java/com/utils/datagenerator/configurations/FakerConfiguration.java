package com.utils.datagenerator.configurations;

import net.datafaker.Faker;

public class FakerConfiguration {

  public static Faker createSimpeFaker() {
    return new Faker();
  }

}
