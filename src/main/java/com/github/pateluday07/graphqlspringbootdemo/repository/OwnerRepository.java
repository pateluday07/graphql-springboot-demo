package com.github.pateluday07.graphqlspringbootdemo.repository;

import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> getByCarsId(Long id);

    Boolean existsByEmail(String email);

    Optional<Owner> getByEmail(String email);
}
