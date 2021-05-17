package com.narola.graphqlspringbootdemo.resolver;

import com.narola.graphqlspringbootdemo.dto.LoginRequestDto;
import com.narola.graphqlspringbootdemo.dto.LoginResponseDto;
import com.narola.graphqlspringbootdemo.dto.RegistrationDto;
import com.narola.graphqlspringbootdemo.service.AuthenticationService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationQueryResolver implements GraphQLQueryResolver {

    private final AuthenticationService authenticationService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        log.info("Authentication query resolver to login: {}", loginRequestDto);
        return authenticationService.login(loginRequestDto);
    }

}
