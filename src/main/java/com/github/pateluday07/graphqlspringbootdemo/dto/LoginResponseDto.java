package com.github.pateluday07.graphqlspringbootdemo.dto;

import lombok.*;

import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.JWT_TOKEN_PREFIX;

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
