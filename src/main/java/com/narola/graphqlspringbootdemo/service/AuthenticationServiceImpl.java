package com.narola.graphqlspringbootdemo.service;

import com.narola.graphqlspringbootdemo.config.security.CustomUserDetail;
import com.narola.graphqlspringbootdemo.config.security.CustomUserDetailService;
import com.narola.graphqlspringbootdemo.config.security.JwtUtil;
import com.narola.graphqlspringbootdemo.dto.LoginRequestDto;
import com.narola.graphqlspringbootdemo.dto.LoginResponseDto;
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
