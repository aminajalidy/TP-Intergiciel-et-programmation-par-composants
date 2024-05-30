package com.uphf.kafka;

import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.time.Duration;
import java.util.Collections;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.TopicPartition;

public class AppWithMoreConsumers {
    public static void main(String[] args) {
        System.out.println("LANCEMENT DU PRODUCER");
        runProducer();

        System.out.println("LANCEMENT DU CONSUMER GÉNÉRIQUE");
        runConsumer();

        if (args.length > 0 && args[0].equalsIgnoreCase("partitionConsumer")) {
            int partition = Integer.parseInt(args[1]);
            System.out.println("LANCEMENT DU CONSUMER POUR LA PARTITION " + partition);
            runPartitionConsumer(partition);
        } 
    }

    /**********************************
     * CONSOMMATEUR GÉNÉRIQUE
     **********************************/
    static void runConsumer() {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();

        // get a reference to the current thread
        final Thread mainThread = Thread.currentThread();

        // adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Detection d'un shutdown, quitter par l'appel de la methode wakeup() du consumer...");
                consumer.wakeup();

                // joindre le thread principal pour autoriser l'execution du code dans le thread principal
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        int noMessageToFetch = 0;
        consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
        
        try {
            while (true) {
                final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                if (consumerRecords.count() == 0) {
                    noMessageToFetch++;
                    if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                        break;
                    else
                        continue;
                }

                consumerRecords.forEach(record -> {
                    System.out.println("Record Key " + record.key());
                    System.out.println("Record value " + record.value());
                    System.out.println("Record partition " + record.partition());
                    System.out.println("Record offset " + record.offset());
                });
                consumer.commitAsync();
            }
        } catch (WakeupException e) {
            System.out.println("Wake up exception!");
            // nous ignorons cette exception qui est attendue pour une fermeture propre
        } catch (Exception e) {
            System.out.println("Unexpected exception \r\n" + e);
        } finally {
            consumer.close(); // this will also commit the offsets if need be.
            System.out.println("Le consumer est maintenant proprement stoppé...");
        }
    }

    /**********************************
     * CONSOMMATEUR POUR UNE PARTITION SPÉCIFIQUE
     **********************************/
    static void runPartitionConsumer(int partitionNumber) {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();

        // get a reference to the current thread
        final Thread mainThread = Thread.currentThread();

        // adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Detection d'un shutdown, quitter par l'appel de la methode wakeup() du consumer...");
                consumer.wakeup();

                // joindre le thread principal pour autoriser l'execution du code dans le thread principal
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        try {
            TopicPartition partition = new TopicPartition(IKafkaConstants.TOPIC_NAME, partitionNumber);
            consumer.assign(Collections.singletonList(partition));

            while (true) {
                final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                if (consumerRecords.count() == 0) {
                    continue;
                }

                consumerRecords.forEach(record -> {
                    System.out.println("Partition " + partitionNumber + " Record Key " + record.key());
                    System.out.println("Partition " + partitionNumber + " Record value " + record.value());
                    System.out.println("Partition " + partitionNumber + " Record offset " + record.offset());
                });
                consumer.commitAsync();
            }
        } catch (WakeupException e) {
            System.out.println("Wake up exception!");
            // nous ignorons cette exception qui est attendue pour une fermeture propre
        } catch (Exception e) {
            System.out.println("Unexpected exception \r\n" + e);
        } finally {
            consumer.close(); // this will also commit the offsets if need be.
            System.out.println("Le consumer pour la partition " + partitionNumber + " est maintenant proprement stoppé...");
        }
    }

    /**********************************
     * PRODUCTEUR
     **********************************/
    static void runProducer() {
        Producer<String, String> producer = ProducerCreator.createProducer();
        int partitionCount = 3; // Exemple : nombre de partitions

        for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
            int partition = index % partitionCount; // Distribution équitable sur les partitions
            final ProducerRecord<String, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, partition, "ID=" + index, "Enregistrement N° " + index);

            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Enregistrement envoyé avec clé " + index + " vers la partition " + metadata.partition() + " et l'offset " + metadata.offset());
            } catch (ExecutionException | InterruptedException e) {
                System.out.println("Erreur dans l'envoi de l'enregistrement");
                System.out.println(e);
            }
        }

        producer.close();
    }
}
