package com.problems.learning.designpatterns.creational.builder.director;

import java.util.Objects;

public class ConnectionDetails {
    // Common
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    // DB-specific
    private final String database;
    private final int maxPoolSize;
    private final int connectionTimeoutMs;

    // Kafka-specific
    private final String groupId;
    private final String securityProtocol;
    private final int retries;

    // Redis-specific
    private final int dbIndex;
    private final boolean useSsl;
    private final long commandTimeoutMs;

    private ConnectionDetails(Builder builder) {
        this.host                = builder.host;
        this.port                = builder.port;
        this.username            = builder.username;
        this.password            = builder.password;
        this.database            = builder.database;
        this.maxPoolSize         = builder.maxPoolSize;
        this.connectionTimeoutMs = builder.connectionTimeoutMs;
        this.groupId             = builder.groupId;
        this.securityProtocol    = builder.securityProtocol;
        this.retries             = builder.retries;
        this.dbIndex             = builder.dbIndex;
        this.useSsl              = builder.useSsl;
        this.commandTimeoutMs    = builder.commandTimeoutMs;
    }

    @Override
    public String toString() {
        return String.format("""
            ConnectionDetails {
              host=%s, port=%d, user=%s,
              database=%s, maxPoolSize=%d, connTimeout=%dms,
              groupId=%s, securityProtocol=%s, retries=%d,
              dbIndex=%d, ssl=%b, cmdTimeout=%dms
            }""",
            host, port, username,
            database, maxPoolSize, connectionTimeoutMs,
            groupId, securityProtocol, retries,
            dbIndex, useSsl, commandTimeoutMs);
    }

    // ----------------------------------------------------------------
    // Builder (inner static class)
    // ----------------------------------------------------------------
    public static class Builder {
        private String host;
        private int    port;
        private String username;
        private String password;

        private String database            = "";
        private int    maxPoolSize         = 0;
        private int    connectionTimeoutMs = 0;

        private String groupId          = "";
        private String securityProtocol = "";
        private int    retries          = 0;

        private int     dbIndex          = 0;
        private boolean useSsl           = false;
        private long    commandTimeoutMs = 0;

        public Builder host(String host)         { this.host = host; return this; }
        public Builder port(int port)             { this.port = port; return this; }
        public Builder username(String username)  { this.username = username; return this; }
        public Builder password(String password)  { this.password = password; return this; }

        // DB
        public Builder database(String db)               { this.database = db; return this; }
        public Builder maxPoolSize(int size)              { this.maxPoolSize = size; return this; }
        public Builder connectionTimeoutMs(int ms)        { this.connectionTimeoutMs = ms; return this; }

        // Kafka
        public Builder groupId(String groupId)            { this.groupId = groupId; return this; }
        public Builder securityProtocol(String protocol)  { this.securityProtocol = protocol; return this; }
        public Builder retries(int retries)               { this.retries = retries; return this; }

        // Redis
        public Builder dbIndex(int index)                 { this.dbIndex = index; return this; }
        public Builder useSsl(boolean ssl)                { this.useSsl = ssl; return this; }
        public Builder commandTimeoutMs(long ms)          { this.commandTimeoutMs = ms; return this; }

        public ConnectionDetails build() {
            Objects.requireNonNull(host, "host must not be null");
            Objects.requireNonNull(username, "username must not be null");
            return new ConnectionDetails(this);
        }
    }
}