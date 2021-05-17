package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.SaveCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateCarDto;

import java.util.List;

public interface CarService {

    CarDto save(SaveCarDto saveCarDto);

    CarDto update(UpdateCarDto updateCarDto);

    CarDto getById(Long id);

    List<CarDto> getAll();

    List<OwnerCarDto> getByOwnerId(Long id);

    void deleteById(Long id);

}
