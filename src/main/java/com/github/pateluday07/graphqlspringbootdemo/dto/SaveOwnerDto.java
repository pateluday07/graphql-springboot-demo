package com.github.pateluday07.graphqlspringbootdemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveOwnerDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private List<OwnerCarDto> cars = new ArrayList<>();

}
