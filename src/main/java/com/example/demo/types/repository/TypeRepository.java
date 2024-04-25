package com.example.demo.types.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.types.model.TypeEntity;

@Repository
public class TypeRepository extends MapRepository<TypeEntity> {
}
