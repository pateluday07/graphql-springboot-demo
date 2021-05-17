package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.LoginRequestDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.LoginResponseDto;

public interface AuthenticationService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
