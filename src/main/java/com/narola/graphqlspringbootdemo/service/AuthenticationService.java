package com.narola.graphqlspringbootdemo.service;

import com.narola.graphqlspringbootdemo.dto.LoginRequestDto;
import com.narola.graphqlspringbootdemo.dto.LoginResponseDto;

public interface AuthenticationService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
