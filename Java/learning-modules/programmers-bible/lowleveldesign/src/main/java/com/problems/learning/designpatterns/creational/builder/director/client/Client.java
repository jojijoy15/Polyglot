package com.problems.learning.designpatterns.creational.builder.director.client;

import com.problems.learning.designpatterns.creational.builder.director.ConnectionDetails;
import com.problems.learning.designpatterns.creational.builder.director.ConnectionDirector;
import com.problems.learning.designpatterns.creational.builder.director.builders.KafkaConnectionBuilder;
import com.problems.learning.designpatterns.creational.builder.director.builders.PostgresConnectionBuilder;
import com.problems.learning.designpatterns.creational.builder.director.builders.RedisConnectionBuilder;

public class Client {
    public static void main(String[] args) {

        // --- PostgreSQL ---
        ConnectionDirector pgDirector = new ConnectionDirector(new PostgresConnectionBuilder());
        ConnectionDetails pgConn = pgDirector.constructConnection(
                "pg-primary.internal", 5432, "app_user", "pg_secret"
        );
        System.out.println("POSTGRES → " + pgConn);

        // --- Kafka ---
        ConnectionDirector kafkaDirector = new ConnectionDirector(new KafkaConnectionBuilder());
        ConnectionDetails kafkaConn = kafkaDirector.constructConnection(
                "kafka-broker.internal", 9093, "kafka_user", "kafka_secret"
        );
        System.out.println("KAFKA → " + kafkaConn);

        // --- Redis ---
        ConnectionDirector redisDirector = new ConnectionDirector(new RedisConnectionBuilder());
        ConnectionDetails redisConn = redisDirector.constructConnection(
                "redis-cache.internal", 6380, "default", "redis_secret"
        );
        System.out.println("REDIS → " + redisConn);
    }
}