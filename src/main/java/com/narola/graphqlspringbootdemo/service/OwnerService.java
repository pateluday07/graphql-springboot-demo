package com.narola.graphqlspringbootdemo.service;

import com.narola.graphqlspringbootdemo.dto.*;
import com.narola.graphqlspringbootdemo.entity.Owner;

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
