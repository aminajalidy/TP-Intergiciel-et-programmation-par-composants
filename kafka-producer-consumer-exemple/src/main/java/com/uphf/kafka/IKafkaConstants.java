package com.uphf.kafka;

public interface IKafkaConstants {
	public static String KAFKA_BROKERS = "localhost:9093";
	
	public static Integer MESSAGE_COUNT=100;
	
	public static String CLIENT_ID="client1";
	
	public static String TOPIC_NAME="HT-topic5";
	
	public static String GROUP_ID_CONFIG="consumerGroup10";
	
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
	
	public static String OFFSET_RESET_EARLIER="earliest";
	
	public static Integer MAX_POLL_RECORDS=1;

    public static String SSL_KEYSTORE_LOCATION = "/home/khady/sslDir/kafka.client.keystore.jks";

	public static String SSL_KEYSTORE_PASSWORD = "pwdtpkafka";

	public static String SSL_KEY_PASSWORD = "pwdtpkafka";

	public static String SSL_TRUSTSTORE_LOCATION = "/home/khady/sslDir/kafka.client.truststore.jks";

	public static String SSL_TRUSTSTORE_PASSWORD = "pwdtpkafka";
}
