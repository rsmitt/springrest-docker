package ru.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.Car;
import ru.edu.exception.ItemNotFoundException;
import ru.edu.exception.ServiceError;
import ru.edu.service.CarService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarService service;

    @Operation(summary = "Get all cars")
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = service.findAll();
        LOGGER.info("getting car list: {}", cars);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /***
     * Method for getting car details
     * @param carId - car id
     * @return car details
     */
    @GetMapping("/{carId}")
    public Car getCarById(@PathVariable long carId) {
        return service.findById(carId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        service.save(car);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/cars/" + car.getId());
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Car> updateCar(@RequestBody Car car) {
        service.update(car);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Car> deleteCarById(@PathVariable long carId) {
        service.deleteById(carId);
        return ResponseEntity.ok().build();
    }

}
