package com.github.pateluday07.graphqlspringbootdemo.resolver;

import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerCarDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.service.CarService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class OwnerResolver implements GraphQLResolver<OwnerDto> {

    private final CarService carService;

    public List<OwnerCarDto> getCars(OwnerDto owner) {
        log.info("Owner resolver to get cars by owner id: {}", owner.getId());
        return carService.getByOwnerId(owner.getId());
    }
}
