package com.narola.graphqlspringbootdemo.resolver;

import com.narola.graphqlspringbootdemo.dto.OwnerDto;
import com.narola.graphqlspringbootdemo.dto.RegistrationDto;
import com.narola.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.narola.graphqlspringbootdemo.service.OwnerService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class OwnerMutationResolver implements GraphQLMutationResolver {

    private final OwnerService ownerService;

    public OwnerDto updateOwner(UpdateOwnerDto updateOwnerDto) {
        log.info("Owner mutation resolver to update owner: {}", updateOwnerDto);
        return ownerService.update(updateOwnerDto);
    }

    public String deleteOwnerById(Long id) {
        log.info("Owner mutation resolver to delete owner by id: {}", id);
        ownerService.deleteById(id);
        return "Owner deleted successfully";
    }
}
