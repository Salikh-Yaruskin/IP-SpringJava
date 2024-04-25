package com.example.demo.geolocations.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.repository.GeolocationRepository;

@Service
public class GeolocationService {
    private final GeolocationRepository repository;

    public GeolocationService(GeolocationRepository repository) {
        this.repository = repository;
    }

    private void checkName(String name) {
        if (repository.findByNameIgnoreCase(name).isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Type with name %s is already exists", name));
        }
    }

    @Transactional(readOnly = true)
    public List<GeolocationEntity> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    @Transactional(readOnly = true)

    public GeolocationEntity get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(GeolocationEntity.class, id));
    }

    @Transactional
    public GeolocationEntity create(GeolocationEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        checkName(entity.getName());
        return repository.save(entity);
    }

    @Transactional
    public GeolocationEntity update(Long id, GeolocationEntity entity) {
        final GeolocationEntity existsEntity = get(id);
        checkName(entity.getName());
        existsEntity.setName(entity.getName());
        return repository.save(existsEntity);
    }

    @Transactional
    public GeolocationEntity delete(Long id) {
        final GeolocationEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }
}
