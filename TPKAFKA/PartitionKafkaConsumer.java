import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PartitionKafkaConsumer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the partition number as an argument.");
            System.exit(1);
        }

        int partitionNumber = Integer.parseInt(args[0]);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // SSL configuration
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", "C:/TPKAFKA/server_certs/client.truststore.jks");
        props.put("ssl.truststore.password", "raniabadi");
        props.put("ssl.keystore.location", "C:/TPKAFKA/server_certs/client.keystore.jks");
        props.put("ssl.keystore.password", "raniabadi");
        props.put("ssl.key.password", "raniabadi");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        TopicPartition partition = new TopicPartition("firsttopic", partitionNumber);
        consumer.assign(Collections.singletonList(partition));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Consumed message: key = %s, value = %s, partition = %d, offset = %d%n",
                        record.key(), record.value(), record.partition(), record.offset());
            }
        }
    }
}
