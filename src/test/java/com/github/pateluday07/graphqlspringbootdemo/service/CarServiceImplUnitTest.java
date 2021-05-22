package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.SaveCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateCarDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Car;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import com.github.pateluday07.graphqlspringbootdemo.exception.CustomException;
import com.github.pateluday07.graphqlspringbootdemo.mapper.CarMapper;
import com.github.pateluday07.graphqlspringbootdemo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplUnitTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;
    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private CarServiceImpl carService;

    private final Owner owner = new Owner();
    private SaveCarDto saveCarDto;
    private UpdateCarDto updateCarDto;
    private CarDto carDto;
    private Car car;

    @BeforeEach
    void saveCarDtoInit() {
        SaveCarDto saveCarDto = new SaveCarDto();
        saveCarDto.setBrand("Porsche");
        saveCarDto.setModel("911 Turbo");
        saveCarDto.setOwnerId(1L);
        this.saveCarDto = saveCarDto;
    }

    @BeforeEach
    void carInit() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Porsche");
        car.setModel("911 Turbo");
        car.setOwner(owner);
        this.car = car;
    }

    @BeforeEach
    void CarDtoInit() {
        CarDto carDto = new CarDto();
        carDto.setId(1L);
        carDto.setBrand("Porsche");
        carDto.setModel("911 Turbo");
        this.carDto = carDto;
    }

    @BeforeEach
    void updateCarDtoInit() {
        UpdateCarDto updateCarDto = new UpdateCarDto();
        updateCarDto.setId(1L);
        updateCarDto.setBrand("Porsche");
        updateCarDto.setModel("911 Turbo");
        updateCarDto.setOwnerId(1L);
        this.updateCarDto = updateCarDto;
    }

    @Test
    @DisplayName("Save car, when data is valid")
    void save_NoException_IfDataIsValid() {
        when(ownerService.getById(anyLong())).thenReturn(owner);
        when(carMapper.toEntity(any(SaveCarDto.class))).thenReturn(car);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        when(carMapper.toDto(any(Car.class))).thenReturn(carDto);
        assertDoesNotThrow(() -> carService.save(saveCarDto));
    }

    @Test
    @DisplayName("Update car, throws exception, if car not found")
    void update_ThrowsException_IfCarNotFound() {
        when(carRepository.existsById(anyLong())).thenReturn(false);
        CustomException customException = assertThrows(CustomException.class, () -> carService.update(updateCarDto));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(CAR_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Update car, no exception, if car data is valid")
    void update_NoException_IfDataIsValid() {
        when(carRepository.existsById(anyLong())).thenReturn(true);
        when(ownerService.getById(anyLong())).thenReturn(owner);
        when(carMapper.toEntity(any(UpdateCarDto.class))).thenReturn(car);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        when(carMapper.toDto(any(Car.class))).thenReturn(carDto);
        assertDoesNotThrow(() -> carService.update(updateCarDto));
    }

    @Test
    @DisplayName("Get by id, throws exception, if car not found")
    void getById_ThrowsException_IfCarNotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        CustomException customException = assertThrows(CustomException.class, () -> carService.getById(1L));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(CAR_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Get by id, no exception, if car found")
    void getById_NoException_IfCarFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        assertDoesNotThrow(() -> carService.getById(1L));
    }

    @Test
    @DisplayName("Get all cars, returns empty list, if cars not found")
    void getAll_EmptyList_IfCarsNotFound() {
        when(carRepository.findAll()).thenReturn(Collections.emptyList());
        List<CarDto> cars = carService.getAll();
        assertTrue(cars.isEmpty());
    }

    @Test
    @DisplayName("Get all cars, returns a list of cars, if cars found")
    void getAll_NonEmptyList_IfCarsFound() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(car, car));
        List<CarDto> cars = carService.getAll();
        assertFalse(cars.isEmpty());
        assertEquals(2,cars.size());
    }

    @Test
    @DisplayName("Get cars by owner's id, return empty list, if car not found")
    void getByOwnerId_EmptyList_IfCarNotFound(){
        when(carRepository.getByOwnerId(anyLong())).thenReturn(Collections.emptyList());
        List<OwnerCarDto> cars = carService.getByOwnerId(1L);
        assertTrue(cars.isEmpty());
    }

    @Test
    @DisplayName("Get cars by owner's id, return a list of cars, if cars found")
    void getByOwnerId_NonEmptyList_IfCarsFound(){
        when(carRepository.getByOwnerId(anyLong())).thenReturn(Arrays.asList(car, car));
        List<OwnerCarDto> cars = carService.getByOwnerId(1L);
        assertFalse(cars.isEmpty());
        assertEquals(2,cars.size());
    }

    @Test
    @DisplayName("Delete car by id, throws exception, if car not found")
    void deleteById_ThrowsException_IfCarNotFound(){
        CustomException customException = assertThrows(CustomException.class, () -> carService.getById(1L));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(CAR_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Delete car by id, delete car, if car found")
    void deleteById_DeleteCar_IfCarFound(){
        when(carRepository.existsById(anyLong())).thenReturn(true);
        assertDoesNotThrow(()->carService.deleteById(anyLong()));
    }
}
