package com.example.demo.types.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.types.model.TypeEntity;

public interface TypeRepository extends CrudRepository<TypeEntity, Long> {
    Optional<TypeEntity> findByNameIgnoreCase(String name);
}
