package com.github.pateluday07.graphqlspringbootdemo.mapper;

import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.SaveCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.CarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateCarDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Car;
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
