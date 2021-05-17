package com.narola.graphqlspringbootdemo.dto;

import lombok.*;

import static com.narola.graphqlspringbootdemo.constant.Constants.JWT_TOKEN_PREFIX;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginResponseDto {

    private String tokenPrefix = JWT_TOKEN_PREFIX;
    private String token;

    public LoginResponseDto(String token){
        this.token = token;
    }
}
