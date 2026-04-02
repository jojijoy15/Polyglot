package com.problems.learning.designpatterns.creational.builder.director.builders;

import com.problems.learning.designpatterns.creational.builder.director.ConnectionDetails;

public class KafkaConnectionBuilder implements ConnectionBuilder {

    private final ConnectionDetails.Builder builder = new ConnectionDetails.Builder();

    @Override
    public ConnectionBuilder setCredentials(String username, String password) {
        builder.username(username).password(password);
        return this;
    }

    @Override
    public ConnectionBuilder setEndpoint(String host, int port) {
        builder.host(host).port(port);
        return this;
    }

    @Override
    public ConnectionBuilder setConnectionOptions() {
        builder.groupId("payment-service-group")
               .securityProtocol("SASL_SSL")
               .retries(5);
        return this;
    }

    @Override
    public ConnectionDetails build() {
        return builder.build();
    }
}