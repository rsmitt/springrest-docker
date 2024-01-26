package ru.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.entity.Car;
import ru.edu.service.CarService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({CarController.class})
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService service;

    @InjectMocks
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCars() throws Exception {
        List<Car> cars = new ArrayList<>(Arrays.asList(
                new Car(1L, "LADA", "AA121212SD", 1990),
                new Car(2L, "BMW", "XC323232BV", 2000)
        ));

        when(service.findAll()).thenReturn(cars);

        mockMvc.perform(get("/api/v1/cars"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("receiving single car details")
    void getCarById() throws Exception {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(service.findById(anyLong())).thenReturn(car);

        mockMvc.perform(get("/api/v1/cars/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", Matchers.hasSize(4)))
                .andExpect(jsonPath("$.name").value("LADA"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("creating a new car")
    void createCar() throws Exception {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(service.save(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(car)))
                .andDo(print())
                .andExpect(header().string("Location", "/api/v1/cars/" + car.getId()))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("updating a car")
    void updateCar() throws Exception {
        Car car = new Car(1L, "LADA", "AA121212SD", 1990);

        when(service.update(any(Car.class))).thenReturn(car);

        mockMvc.perform(put("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(car)))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    void deleteCarById() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        mockMvc.perform(delete("/api/v1/cars/1"))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    @Disabled("used for controller with view templates")
    public void someTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("author", Matchers.equalTo("Petr Petrov")));
    }

}