package com.uphf.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;


public class ProducerCreator {

        public static Producer<String, String> createProducer() {
		
		Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Configurer la sécurité SSL
        props.put("security.protocol", "SSL");
        props.put("ssl.keystore.location", "/home/khady/sslDir/kafka.client.keystore.jks");
        props.put("ssl.keystore.password", "pwdtpkafka");
        props.put("ssl.key.password", "pwdtpkafka");
        props.put("ssl.truststore.location", "/home/khady/sslDir/kafka.client.truststore.jks");
        props.put("ssl.truststore.password", "pwdtpkafka");


		return new KafkaProducer<>(props);
	}
}