import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class PartitionKafkaProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // SSL configuration
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", "C:/TPKAFKA/server_certs/client.truststore.jks");
        props.put("ssl.truststore.password", "raniabadi");
        props.put("ssl.keystore.location", "C:/TPKAFKA/server_certs/client.keystore.jks");
        props.put("ssl.keystore.password", "raniabadi");
        props.put("ssl.key.password", "raniabadi");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        AtomicInteger partitionCounter = new AtomicInteger(0);
        int numPartitions = 3;  // Change this to the number of partitions in your topic

        for (int i = 0; i < 10; i++) {
            int partition = partitionCounter.getAndIncrement() % numPartitions;
            ProducerRecord<String, String> record = new ProducerRecord<>("firsttopic", partition, Integer.toString(i), "Message " + i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.err.println("Error sending message: " + exception.getMessage());
                    } else {
                        System.out.println("Message sent to topic: " + metadata.topic() + " partition: " + metadata.partition() + " offset: " + metadata.offset());
                    }
                }
            });
        }

        producer.close();
    }
}
