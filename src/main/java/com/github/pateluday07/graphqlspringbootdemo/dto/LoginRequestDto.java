package com.github.pateluday07.graphqlspringbootdemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto {

    private String email;
    @ToString.Exclude
    private String password;
}
