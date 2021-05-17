package com.narola.graphqlspringbootdemo.resolver;

import com.narola.graphqlspringbootdemo.dto.CarDto;
import com.narola.graphqlspringbootdemo.dto.CarOwnerDto;
import com.narola.graphqlspringbootdemo.dto.OwnerCarDto;
import com.narola.graphqlspringbootdemo.dto.OwnerDto;
import com.narola.graphqlspringbootdemo.service.OwnerService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CarResolver implements GraphQLResolver<CarDto> {

    private final OwnerService ownerService;

    public CarOwnerDto getOwner(CarDto car){
        log.info("Car resolver to get owner by car id: {}", car.getId());
        return ownerService.getByCarId(car.getId());
    }
}
