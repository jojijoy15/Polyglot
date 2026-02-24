package org.kafka.sample.playground.eventsource;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.kafka.sample.playground.producer.RealtimeProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiMediaEventHandler implements BackgroundEventHandler {

  private static final Logger logger = LoggerFactory.getLogger(RealtimeProducer.class.getName());

  private KafkaProducer<String, String> producer;
  private String topic;

  public WikiMediaEventHandler(KafkaProducer<String, String> producer, String topicName) {
    this.producer = producer;
    this.topic = topicName;
  }

  @Override
  public void onOpen() {
    logger.info("Stream opened");
  }

  @Override
  public void onClosed() {
    producer.close();
    logger.info("Stream closed");
  }

  @Override
  public void onMessage(String s, MessageEvent messageEvent) {
    ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, messageEvent.getEventName(), messageEvent.getData());
    this.producer.send(record);
    logger.info("Message send successfully with message event : {}", messageEvent.getEventName());
  }

  @Override
  public void onComment(String s) {

  }

  @Override
  public void onError(Throwable throwable) {
    logger.error("Error happened while consuming event from Wikimedia event stream", throwable);
  }
}
