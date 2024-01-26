package ru.edu.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.edu.entity.Car;
import ru.edu.exception.ItemNotFoundException;
import ru.edu.repository.CarRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository repository;

    private CarService service;

    @BeforeEach
    void setUp() {
        service = new CarService(repository);
    }


    @Test
    void findAll() {
        List<Car> cars = new ArrayList<>(Arrays.asList(
                new Car(1L, "LADA", "AA121212SD", 1990),
                new Car(2L, "BMW", "XC323232BV", 2000)
        ));

        when(repository.findAll()).thenReturn(cars);

        List<Car> allCars = service.findAll();
        assertThat(allCars.size()).isGreaterThan(0);
    }

    @Test
    void findById() {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(repository.findById(1L)).thenReturn(Optional.of(car));

        Car singleCar = service.findById(1L);
        assertThat(singleCar).isNotNull().isEqualTo(car);
    }

    @Test
    void save() {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(repository.save(car)).thenReturn(car);

        Car savedCar = service.save(car);
        assertThat(savedCar.getName()).isNotNull();
    }

    @Test
    void update() {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(repository.findById(anyLong())).thenReturn(Optional.of(car));
        when(repository.save(car)).thenReturn(car);

        Car savedCar = service.update(car);
        assertThat(savedCar.getName()).isNotNull();
    }

    @Test
    void deleteById() {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(repository.findById(anyLong())).thenReturn(Optional.of(car));
        doNothing().when(repository).deleteById(anyLong());

        service.deleteById(1L);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void ItemNotFoundException() {
        ItemNotFoundException exception = Assertions.assertThrows(ItemNotFoundException.class, () -> service.deleteById(1L));
        assertThat(exception.getMessage()).isEqualTo("Car not found, id = 1");
    }
}