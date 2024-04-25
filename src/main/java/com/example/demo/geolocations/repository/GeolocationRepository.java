package com.example.demo.geolocations.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.geolocations.model.GeolocationEntity;

public interface GeolocationRepository extends CrudRepository<GeolocationEntity, Long> {
    Optional<GeolocationEntity> findByNameIgnoreCase(String name);
}
