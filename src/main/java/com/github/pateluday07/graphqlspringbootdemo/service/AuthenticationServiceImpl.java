package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.LoginRequestDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.LoginResponseDto;
import com.github.pateluday07.graphqlspringbootdemo.config.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        log.info("Authentication service to login: {}", loginRequestDto);
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        return new LoginResponseDto(jwtUtil.generateToken((UserDetails) authenticate.getPrincipal()));
    }
}
