package com.narola.graphqlspringbootdemo.resolver;

import com.narola.graphqlspringbootdemo.dto.*;
import com.narola.graphqlspringbootdemo.service.CarService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CarMutationResolver implements GraphQLMutationResolver {

    private final CarService carService;

    public CarDto saveCar(SaveCarDto saveCarDto){
        log.info("Car mutation resolver to save car: {}", saveCarDto);
        return carService.save(saveCarDto);
    }

    public CarDto updateCar(UpdateCarDto updateCarDto){
        log.info("Car mutation resolver to update car: {}", updateCarDto);
        return carService.update(updateCarDto);
    }

    public String deleteCarById(Long id){
        log.info("Car mutation resolver to delete car by id: {}", id);
        carService.deleteById(id);
        return "Car deleted successfully";
    }
}
