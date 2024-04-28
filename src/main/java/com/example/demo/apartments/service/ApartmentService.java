package com.example.demo.apartments.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.repository.ApartmentRepository;
import com.example.demo.core.error.NotFoundException;
import com.example.demo.geolocations.repository.GeolocationRepository;
import com.example.demo.types.repository.TypeRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ApartmentService {
    private final ApartmentRepository repository;
    private final TypeRepository typeRepository;
    private final GeolocationRepository geolocationRepository;

    public ApartmentService(ApartmentRepository repository, TypeRepository typeRepository,
            GeolocationRepository geolocationRepository) {
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.geolocationRepository = geolocationRepository;
    }

    @Transactional(readOnly = true)
    public List<ApartmentEntity> getAll(Long typeId, Long geolocationId) {
        if (typeId <= 0L || geolocationId <= 0) {
            return (List<ApartmentEntity>) repository.findAll();
        }
        return repository.findByTypeIdAndGeolocationId(typeId, geolocationId);
    }

    @Transactional(readOnly = true)
    public ApartmentEntity get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApartmentEntity.class, id));
    }

    @Transactional
    public ApartmentEntity create(ApartmentEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        return repository.save(entity);
    }

    @Transactional
    public ApartmentEntity update(Long id, ApartmentEntity entity) {
        final ApartmentEntity existsEntity = get(id);
        existsEntity.setType(entity.getType());
        existsEntity.setPropertyStatus(entity.getPropertyStatus());
        existsEntity.setPopular(entity.getPopular());
        existsEntity.setPrice(entity.getPrice());
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getDescription());
        existsEntity.setGeolocation(entity.getGeolocation());
        existsEntity.setShower(entity.getShower());
        existsEntity.setPark(entity.getPark());
        return repository.save(existsEntity);
    }

    @Transactional
    public ApartmentEntity delete(Long id) {
        final ApartmentEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }
}
