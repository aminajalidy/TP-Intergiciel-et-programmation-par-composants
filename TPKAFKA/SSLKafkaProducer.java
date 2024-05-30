import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SSLKafkaProducer {
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

        AdminClient adminClient = AdminClient.create(props);
        try {
            DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(java.util.Collections.singletonList("firsttopic"));
            describeTopicsResult.all().get();
            System.out.println("Topic 'firsttopic' exists. Sending messages...");

            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("firsttopic", Integer.toString(i), "Enregistrement N° " + i);
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception != null) {
                            System.err.println("Error sending message: " + exception.getMessage());
                        } else {
                            System.out.printf("Enregistrement envoye avec cle %s vers la partition %d et l'offset %d%n",
                                    record.key(), metadata.partition(), metadata.offset());
                        }
                    }
                });
            }

        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Topic 'firsttopic' does not exist or error occurred: " + e.getMessage());
        } finally {
            adminClient.close();
            producer.close();
        }
    }
}
