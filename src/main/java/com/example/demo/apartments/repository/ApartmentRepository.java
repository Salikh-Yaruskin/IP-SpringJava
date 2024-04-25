package com.example.demo.apartments.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.core.repository.MapRepository;

@Repository
public class ApartmentRepository extends MapRepository<ApartmentEntity> {
}
