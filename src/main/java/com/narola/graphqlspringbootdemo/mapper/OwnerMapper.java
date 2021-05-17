package com.narola.graphqlspringbootdemo.mapper;

import com.narola.graphqlspringbootdemo.dto.CarOwnerDto;
import com.narola.graphqlspringbootdemo.dto.OwnerDto;
import com.narola.graphqlspringbootdemo.dto.RegistrationDto;
import com.narola.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.narola.graphqlspringbootdemo.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    @Mapping(target = "cars", source = "cars", ignore = true)
    OwnerDto toDto(Owner owner);

    CarOwnerDto toCarOwnerDto(Owner owner);

    Owner toEntity(RegistrationDto registrationDto);

    Owner toEntity(UpdateOwnerDto updateOwnerDto);
}
