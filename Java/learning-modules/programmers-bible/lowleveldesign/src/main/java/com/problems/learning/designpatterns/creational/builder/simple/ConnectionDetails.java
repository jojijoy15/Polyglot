package com.problems.learning.designpatterns.creational.builder.simple;

import lombok.ToString;

@ToString
public class ConnectionDetails {

    private String userName;
    private String password;
    private String host;
    private short port;

    private ConnectionDetails(String userName, String password, String host, short port) {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public static class ConnectionDetailsBuilder {

        private String userName;
        private String password;
        private String host;
        private short port;

        public ConnectionDetailsBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public ConnectionDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public ConnectionDetailsBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public ConnectionDetailsBuilder withPort(short port) {
            this.port = port;
            return this;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public String getHost() {
            return host;
        }

        public short getPort() {
            return port;
        }

        public ConnectionDetails build() {
            return new ConnectionDetails(userName, password, host, port);
        }
    }

}
