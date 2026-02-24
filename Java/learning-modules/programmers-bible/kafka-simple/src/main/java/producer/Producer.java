package producer;

import common.Configuration;
import common.FileOps;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

  public static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());

  private static final String CLASSPATH_RESOURCES_PRODUCER_PROPERTIES = "producer/producer.properties";
  private static final String CLASSPATH_RESOURCES_PRODUCER_WITHOUT_KEY_DATA = "producer/producer-data-no-key.txt";
  private static final String CLASSPATH_RESOURCES_PRODUCER_WITH_KEY_DATA = "producer/producer-data-with-key.txt";

  public static void main(String[] args)  {
    Properties configuration = Configuration.retrieveConfigFromClassPath(
        CLASSPATH_RESOURCES_PRODUCER_PROPERTIES);
    //Create Kafka Producer
    KafkaProducer<String, String> producer = new KafkaProducer<>(configuration);
    //Create Producer Record, make sure topic is already created
    final String topicName = "first.demo.topic";
    //Send data asynchronous ops
    //producer.send(producerRecord);
    //sendDataWithoutKey(topicName, producer);
    final ExecutorService executorService = Executors
        .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    executorService.submit(() -> sendDataWithKey(topicName, producer));
    executorService.submit(() -> sendDataWithKey(topicName, producer));
    executorService.submit(() -> sendDataWithKey(topicName, producer));
    executorService.submit(() -> sendDataWithKey(topicName, producer));
    //Flush i.e send data and block until done
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    producer.flush();
    //Flush and close
    producer.close();
    executorService.shutdownNow();
  }

  private static void sendDataWithKey(String topicName, KafkaProducer<String, String> producer) {
    final List<String> lines = FileOps.readFile(CLASSPATH_RESOURCES_PRODUCER_WITH_KEY_DATA);
    lines.stream()
        .forEach(line -> {
          String[] parts = line.split(":");
          ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, parts[0], parts[1]);
          producer.send(producerRecord, (onComplete, onError) -> {
            if (null == onError) {
              logger.info("PRODUCER::Record meta data offset: {} | partition: {}",
                          onComplete.offset(),
                          onComplete.partition());
            }
          });
        });
  }

  private static void sendDataWithoutKey(String topicName, KafkaProducer<String, String> producer) {
    final List<String> lines = FileOps.readFile(CLASSPATH_RESOURCES_PRODUCER_WITHOUT_KEY_DATA);

    lines.stream()
        .forEach(line -> {
          ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, line);
          producer.send(producerRecord, (onComplete, onError) -> {
            if (null == onError) {
              logger.info("PRODUCER::Record meta data key: {} | offset: {} | partition: {}",
                          onComplete.partition(),
                          onComplete.offset(),
                          onComplete.partition());
            }
          });
        });
  }
}
