package com.narola.graphqlspringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OwnerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private List<OwnerCarDto> cars = new ArrayList<>();
}
