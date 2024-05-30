
1. Configuration SSL
  - Keystore et Truststore
  - Keystore : C:/TPKAFKA/server_certs/client.keystore.jks
  - Truststore : C:/TPKAFKA/server_certs/client.truststore.jks
  - Mot de passe : raniabadi

  2. Création du Topic
./kafka-topics.sh --create --bootstrap-server localhost:9093 --replication-factor 1 --partitions 3 --topic firsttopic

   3. Lancement des Consommateurs
java -cp ".;C:/TPKAFKA/libs/kafka-clients-2.7.0.jar;C:/TPKAFKA/libs/slf4j-api-1.7.30.jar;C:/TPKAFKA/libs/slf4j-simple-1.7.30.ar" PajrtitionKafkaConsumer 0
java -cp ".;C:/TPKAFKA/libs/kafka-clients-2.7.0.jar;C:/TPKAFKA/libs/slf4j-api-1.7.30.jar;C:/TPKAFKA/libs/slf4j-simple-1.7.30.jar" PartitionKafkaConsumer 1
java -cp ".;C:/TPKAFKA/libs/kafka-clients-2.7.0.jar;C:/TPKAFKA/libs/slf4j-api-1.7.30.jar;C:/TPKAFKA/libs/slf4j-simple-1.7.30.jar" PartitionKafkaConsumer 2

  4. Executer Producteur
java -cp ".;C:\TPKAFKA\libs\kafka-clients-2.7.0.jar;C:\TPKAFKA\libs\slf4j-api-1.7.30.jar;C:\TPKAFKA\libs\slf4j-simple-1.7.30.jar" PartitionKafkaProducer