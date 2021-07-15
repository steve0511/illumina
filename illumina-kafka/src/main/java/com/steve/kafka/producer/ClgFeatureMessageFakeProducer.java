package com.steve.kafka.producer;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by stevexu on 1/16/17.
 */
public class ClgFeatureMessageFakeProducer {

    static Producer<String, String> producer;

    private static final Logger logger = LoggerFactory.getLogger(ClgFeatureMessageFakeProducer.class);

    public static void main(String[] args) throws Exception {
        InputStream impressionInputStream = ClgFeatureMessageFakeProducer.class.getClassLoader()
            .getResourceAsStream("live_impression_template.properties");

        String impressionTemplate = "";
        Scanner scanner = new Scanner(impressionInputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().replace("\n", "").trim();
            impressionTemplate = line;
        }

        InputStream clickInputStream = ClgFeatureMessageFakeProducer.class.getClassLoader()
            .getResourceAsStream("live_click_template.properties");
        String clickTemplate = "";
        scanner = new Scanner(clickInputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().replace("\n", "").trim();
            clickTemplate = line;
        }
        initProducer();
        sendBatch(producer, "sxu_clg_v0", impressionTemplate, clickTemplate);
    }

    public static void initProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(props);
        producer = kafkaProducer;
    }

    public static void sendBatch(Producer<String, String> producer, String topic, String impressionTemplate,
        String clickTemplate) throws InterruptedException {
    	for (int i =1; i <= 10; i++) {
			for (int round = 1; round <= 500; round++) {
				String streamId = "[100001, 100002, 100003]";
				send(producer, topic, impressionTemplate, streamId);
			}
			for (int round = 1; round <= 400; round++) {
				String streamId = "[100004,100005,100006]";
				send(producer, topic, impressionTemplate, streamId);
			}
			for (int round = 1; round <= 300; round++) {
				String streamId = "100007,100008,100009";
				send(producer, topic, impressionTemplate, streamId);
			}
			for (int round = 1; round <= 200; round++) {
				String streamId = "[100001,100003]";
				send(producer, topic, impressionTemplate, streamId);
			}
			for (int round = 1; round <= 100; round++) {
				String streamId = "100002";
				send(producer, topic, impressionTemplate, streamId);
			}
			for (int round = 1; round <= 50; round++) {
				String streamId = "[100001, 100002, 100003]";
				send(producer, topic, clickTemplate, streamId);
			}
			for (int round = 1; round <= 40; round++) {
				String streamId = "[100004,100005,100006]";
				send(producer, topic, clickTemplate, streamId);
			}
			for (int round = 1; round <= 30; round++) {
				String streamId = "100007,100008,100009";
				send(producer, topic, clickTemplate, streamId);
			}
			for (int round = 1; round <= 20; round++) {
				String streamId = "[100001,100003]";
				send(producer, topic, clickTemplate, streamId);
			}
			for (int round = 1; round <= 10; round++) {
				String streamId = "100002";
				send(producer, topic, clickTemplate, streamId);
			}
			Thread.sleep(1000 * 5);
		}

        producer.close();
    }

    private static void send(Producer<String, String> producer, String topic,
        String template, String streamId) {
        String payload = template.replace("$$$", streamId);
        ProducerRecord<String, String> message = new ProducerRecord<>(
            topic, String.valueOf(streamId), payload);
        producer.send(message, (RecordMetadata recordMetadata, Exception e) -> {
        });
    }

}
