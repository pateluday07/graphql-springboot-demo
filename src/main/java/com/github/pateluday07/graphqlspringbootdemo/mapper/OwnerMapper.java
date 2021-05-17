package com.github.pateluday07.graphqlspringbootdemo.mapper;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.RegistrationDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
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
