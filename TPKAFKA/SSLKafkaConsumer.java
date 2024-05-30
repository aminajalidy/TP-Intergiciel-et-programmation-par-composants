import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class SSLKafkaConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // SSL configuration
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", "C:/TPKAFKA/server_certs/client.truststore.jks");
        props.put("ssl.truststore.password", "raniabadi");
        props.put("ssl.keystore.location", "C:/TPKAFKA/server_certs/client.keystore.jks");
        props.put("ssl.keystore.password", "raniabadi");
        props.put("ssl.key.password", "raniabadi");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        TopicPartition partition0 = new TopicPartition("firsttopic", 0);
        TopicPartition partition1 = new TopicPartition("firsttopic", 1);
        TopicPartition partition2 = new TopicPartition("firsttopic", 2);
        consumer.assign(Arrays.asList(partition0, partition1, partition2));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumed message: key = %s, value = %s, partition = %d, offset = %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            System.err.println("Error while consuming messages: " + e.getMessage());
        } finally {
            consumer.close();
        }
    }
}
