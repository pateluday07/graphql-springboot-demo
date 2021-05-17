package com.narola.graphqlspringbootdemo.repository;

import com.narola.graphqlspringbootdemo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> getByOwnerId(Long id);
}
