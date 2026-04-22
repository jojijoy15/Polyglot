package com.problems.learning.designpatterns.creational.builder.director.builders;

import com.problems.learning.designpatterns.creational.builder.director.ConnectionDetails;

public interface ConnectionBuilder {
    ConnectionBuilder setCredentials(String username, String password);

    ConnectionBuilder setEndpoint(String host, int port);

    ConnectionBuilder setConnectionOptions();   // type-specific options

    ConnectionDetails build();
}