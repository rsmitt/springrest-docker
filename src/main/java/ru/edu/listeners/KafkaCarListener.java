package ru.edu.listeners;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.edu.entity.Car;

@Profile("kafka")
@Log
@Component
public class KafkaCarListener {

    @KafkaListener(topics = "car-topic", groupId = "car-group")
    public void consume(Car car) {
        log.info("received message: " + car);
    }
}
