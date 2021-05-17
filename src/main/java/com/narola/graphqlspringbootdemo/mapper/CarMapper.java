package com.narola.graphqlspringbootdemo.mapper;

import com.narola.graphqlspringbootdemo.dto.CarDto;
import com.narola.graphqlspringbootdemo.dto.OwnerCarDto;
import com.narola.graphqlspringbootdemo.dto.SaveCarDto;
import com.narola.graphqlspringbootdemo.dto.UpdateCarDto;
import com.narola.graphqlspringbootdemo.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toEntity(SaveCarDto saveCarDto);

    Car toEntity(UpdateCarDto updateCarDto);

    @Mapping(target = "owner", source = "owner", ignore = true)
    CarDto toDto(Car car);

    OwnerCarDto toOwnerCarDto(Car car);
}
