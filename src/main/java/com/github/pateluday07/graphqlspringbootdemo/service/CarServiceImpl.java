package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.constant.Constants;
import com.github.pateluday07.graphqlspringbootdemo.dto.CarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.SaveCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateCarDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Car;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import com.github.pateluday07.graphqlspringbootdemo.exception.CustomException;
import com.github.pateluday07.graphqlspringbootdemo.mapper.CarMapper;
import com.github.pateluday07.graphqlspringbootdemo.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final OwnerService ownerService;

    @Override
    @Transactional
    public CarDto save(SaveCarDto saveCarDto) {
        log.info("Service method to save car: {}", saveCarDto);
        Owner owner = ownerService.getById(saveCarDto.getOwnerId());
        Car car = carMapper.toEntity(saveCarDto);
        car.setOwner(owner);
        car = carRepository.save(car);
        log.info("Saved car: {}", car);
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public CarDto update(UpdateCarDto updateCarDto) {
        log.info("Service method to update car: {}", updateCarDto);
        checkIfCarExistsById(updateCarDto.getId());
        Owner owner = ownerService.getById(updateCarDto.getOwnerId());
        Car car = carMapper.toEntity(updateCarDto);
        car.setOwner(owner);
        car = carRepository.save(car);
        log.info("Updated car: {}", car);
        return carMapper.toDto(car);
    }

    @Override
    public CarDto getById(Long id) {
        log.info("Service method to get car by id: {}", id);
        return carMapper
                .toDto(carRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(Constants.NOT_FOUND_ERROR_CODE, Constants.CAR_NOT_FOUND_ERROR_MSG)));
    }

    @Override
    public List<CarDto> getAll() {
        log.info("Service method to get all cars");
        return carRepository
                .findAll()
                .parallelStream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerCarDto> getByOwnerId(Long id) {
        log.info("Car service to get cars by owner id: {}", id);
        return carRepository
                .getByOwnerId(id)
                .parallelStream()
                .map(carMapper::toOwnerCarDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Service method to delete car by id: {}", id);
        checkIfCarExistsById(id);
        carRepository.deleteById(id);
    }

    private void checkIfCarExistsById(Long id) {
        if (!carRepository.existsById(id))
            throw new CustomException(Constants.NOT_FOUND_ERROR_CODE, Constants.CAR_NOT_FOUND_ERROR_MSG);
    }
}
