package com.github.pateluday07.graphqlspringbootdemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

}
