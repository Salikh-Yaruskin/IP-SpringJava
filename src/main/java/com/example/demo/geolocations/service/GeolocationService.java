package com.example.demo.geolocations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.repository.GeolocationRepository;

@Service
public class GeolocationService {
    private final GeolocationRepository repository;

    public GeolocationService(GeolocationRepository repository) {
        this.repository = repository;
    }

    public List<GeolocationEntity> getAll() {
        return repository.getAll();
    }

    public GeolocationEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public GeolocationEntity create(GeolocationEntity entity) {
        return repository.create(entity);
    }

    public GeolocationEntity update(Long id, GeolocationEntity entity) {
        final GeolocationEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.update(existsEntity);
    }

    public GeolocationEntity delete(Long id) {
        final GeolocationEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
