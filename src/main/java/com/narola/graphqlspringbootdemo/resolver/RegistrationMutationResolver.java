package com.narola.graphqlspringbootdemo.resolver;

import com.narola.graphqlspringbootdemo.dto.RegistrationDto;
import com.narola.graphqlspringbootdemo.service.OwnerService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RegistrationMutationResolver implements GraphQLMutationResolver {

    private final OwnerService ownerService;

    public String registerOwner(RegistrationDto registrationDto) {
        log.info("Owner mutation resolver to register owner: {}", registrationDto);
        ownerService.register(registrationDto);
        return "Owner Registered successfully";
    }
}
