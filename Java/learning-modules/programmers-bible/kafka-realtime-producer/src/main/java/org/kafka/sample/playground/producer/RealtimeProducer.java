package org.kafka.sample.playground.producer;

import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.kafka.sample.playground.common.Configuration;
import org.kafka.sample.playground.eventsource.WikiMediaEventHandler;
import org.kafka.sample.playground.eventsource.WikiMediaEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealtimeProducer {

  private static final Logger logger = LoggerFactory.getLogger(RealtimeProducer.class.getName());

  private static final String CLASSPATH_RESOURCES_PRODUCER_PROPERTIES = "producer/producer.properties";

  public static void main(String[] args) {
    final Properties properties = Configuration
        .retrieveConfigFromClassPath(CLASSPATH_RESOURCES_PRODUCER_PROPERTIES);
    KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
    String topicName = "wikimedia.event.recentchange";

    BackgroundEventHandler eventHandler = new WikiMediaEventHandler(kafkaProducer, topicName);
    BackgroundEventSource eventSource = new WikiMediaEventSource().createEventSource(eventHandler);

    /*
    EventHandler eventHandler = new WikiMediaEventHandler(kafkaProducer, topicName);
    EventSource eventSource = new WikiMediaEventSource().createEventSource(eventHandler);
    */

    eventSource.start();
    try {
      Thread.currentThread().join(60000);
    }catch (InterruptedException e) {
      logger.info("Interrupted", e);
    }
    eventSource.close();
    kafkaProducer.close();

  }
}
