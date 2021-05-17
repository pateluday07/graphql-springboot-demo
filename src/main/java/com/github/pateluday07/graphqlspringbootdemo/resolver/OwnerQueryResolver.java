package com.github.pateluday07.graphqlspringbootdemo.resolver;

import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.service.OwnerService;
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
public class OwnerQueryResolver implements GraphQLQueryResolver {

    private final OwnerService ownerService;

    public OwnerDto getOwnerById(Long id) {
        log.info("Owner query resolver to get owner by id: {}", id);
        return ownerService.getDtoById(id);
    }

    public List<OwnerDto> getOwners() {
        log.info("Owner query resolver to get all owners");
        return ownerService.getAll();
    }
}
