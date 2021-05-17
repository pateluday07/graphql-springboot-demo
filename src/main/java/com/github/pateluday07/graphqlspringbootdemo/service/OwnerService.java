package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.RegistrationDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;

import java.util.List;

public interface OwnerService {

    void register(RegistrationDto registrationDto);

    OwnerDto update(UpdateOwnerDto updateOwnerDto);

    OwnerDto getDtoById(Long id);

    Owner getById(Long id);

    CarOwnerDto getByCarId(Long id);

    List<OwnerDto> getAll();

    void deleteById(Long id);
}
