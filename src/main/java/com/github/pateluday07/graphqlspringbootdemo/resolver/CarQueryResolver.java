package com.github.pateluday07.graphqlspringbootdemo.resolver;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarDto;
import com.github.pateluday07.graphqlspringbootdemo.service.CarService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CarQueryResolver implements GraphQLQueryResolver {

    private final CarService carService;

    public CarDto getCarById(Long id){
        log.info("Car query resolver to get car by id: {}", id);
        return carService.getById(id);
    }

    public List<CarDto> getCars(){
        log.info("Car query resolver to get all cars");
        return carService.getAll();
    }
}
