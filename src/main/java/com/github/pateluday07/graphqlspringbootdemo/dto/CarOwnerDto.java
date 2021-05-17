package com.github.pateluday07.graphqlspringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CarOwnerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
