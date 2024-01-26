package ru.edu.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.edu.entity.Car;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Profile("kafka")
@AllArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Car> kafkaTemplate;

    public void sendNotification() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Lada");
        car.setNumber("AQW-12343");

        List<Header> headers = List.of(
                new RecordHeader("custom-header", "some data".getBytes(StandardCharsets.UTF_8))
        );

        //ProducerRecord<String, Car> record = new ProducerRecord<>("car-topic", null, "topic-key", car, headers);
        kafkaTemplate.send("car-topic", car);
    }
}
