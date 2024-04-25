package com.example.demo.geolocations.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.geolocations.model.GeolocationEntity;

@Repository
public class GeolocationRepository extends MapRepository<GeolocationEntity> {
}
