package consumer;

import common.Configuration;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

  private static final String CLASSPATH_RESOURCES_CONSUMER_PROPERTIES = "consumer/consumer.properties";
  private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

  public static void main(String[] args) {
    Properties configuration = Configuration.retrieveConfigFromClassPath(
        CLASSPATH_RESOURCES_CONSUMER_PROPERTIES);
    //Create Kafka Producer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configuration);
    //Create Producer Record, make sure topic is already created
    final String topicName = "first.demo.topic";
    consumer.subscribe(Collections.singleton(topicName)); //Note: this method is overloaded

    // Add shutdown hook for graceful shutdown
    final Thread currentThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread()  {

      @Override
      public void run() {
        logger.info("Executing shutdown hook");
        consumer.wakeup(); // Throw wakeup exception on next call to pool from while loop
        try {
          currentThread.join();
        } catch(InterruptedException ex) {
          logger.error("Exception occurred : {}", ex.getMessage());
        }
      }
    });

    try {
      //Receive data
      while (true) {
        final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
        records.forEach(record -> {
            logger.info(
                "CONSUMER::Data received details: key: {} | value : {} | headers : {} | offset : {} | partition: {}",
                record.key(), record.value(), record.headers(), record.offset(), record.partition());
          }
        );
      }
    } catch (WakeupException e) {
     logger.error("Wakeup Exception occurred while polling...");
    } catch (Exception e) {
      logger.error("Exception occurred while polling...");
    } finally {
      consumer.close(); //close consumer and commit offset
    }

  }
}
