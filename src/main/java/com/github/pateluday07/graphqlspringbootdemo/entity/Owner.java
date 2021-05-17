package com.github.pateluday07.graphqlspringbootdemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@ToString
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 3)
    private Integer age;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car){
        cars.add(car);
        car.setOwner(this);
    }

    public void removeCar(Car car){
        cars.remove(car);
        car.setOwner(null);
    }

}
