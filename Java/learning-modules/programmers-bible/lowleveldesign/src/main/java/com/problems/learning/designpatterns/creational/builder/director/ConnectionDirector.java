package com.problems.learning.designpatterns.creational.builder.director;

import com.problems.learning.designpatterns.creational.builder.director.builders.ConnectionBuilder;

public class ConnectionDirector {

    private final ConnectionBuilder builder;

    public ConnectionDirector(ConnectionBuilder builder) {
        this.builder = builder;
    }

    /**
     * Standard construction order enforced here.
     * Client never has to worry about calling steps in the wrong order.
     */
    public ConnectionDetails constructConnection(
            String host, int port,
            String username, String password) {

        return builder
                .setEndpoint(host, port)        // Step 1: always endpoint first
                .setCredentials(username, password)  // Step 2: credentials
                .setConnectionOptions()         // Step 3: type-specific options
                .build();
    }
}