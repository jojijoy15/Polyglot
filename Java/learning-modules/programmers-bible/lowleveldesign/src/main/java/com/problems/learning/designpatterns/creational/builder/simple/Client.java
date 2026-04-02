package com.problems.learning.designpatterns.creational.builder.simple;

public class Client {

    public static void main(String[] args) {
        ConnectionDetails.ConnectionDetailsBuilder builder = new ConnectionDetails.ConnectionDetailsBuilder();
        builder.withHost("localhost");
        builder.withPort((short)1234);
        ConnectionDetails connectionDetails = builder.build();
        System.out.println(connectionDetails);
    }
}
