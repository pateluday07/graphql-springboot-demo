package com.narola.graphqlspringbootdemo.service;

import com.narola.graphqlspringbootdemo.dto.CarDto;
import com.narola.graphqlspringbootdemo.dto.OwnerCarDto;
import com.narola.graphqlspringbootdemo.dto.SaveCarDto;
import com.narola.graphqlspringbootdemo.dto.UpdateCarDto;

import java.util.List;

public interface CarService {

    CarDto save(SaveCarDto saveCarDto);

    CarDto update(UpdateCarDto updateCarDto);

    CarDto getById(Long id);

    List<CarDto> getAll();

    List<OwnerCarDto> getByOwnerId(Long id);

    void deleteById(Long id);

}
