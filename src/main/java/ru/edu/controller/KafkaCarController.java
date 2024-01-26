package ru.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.service.KafkaService;

@Profile("kafka")
@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class KafkaCarController {

    private final KafkaService service;

    @GetMapping("/notification")
    public ResponseEntity<?> sendNotification() {
        service.sendNotification();
        return ResponseEntity.ok().build();
    }
}
