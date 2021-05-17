package com.github.pateluday07.graphqlspringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateCarDto {

    private Long id;
    private String brand;
    private String model;
    private Long ownerId;
}
