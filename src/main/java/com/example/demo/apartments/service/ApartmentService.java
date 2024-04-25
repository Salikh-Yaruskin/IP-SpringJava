package com.example.demo.apartments.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.repository.ApartmentRepository;
import com.example.demo.core.error.NotFoundException;

@Service
public class ApartmentService {
    private final ApartmentRepository repository;

    public ApartmentService(ApartmentRepository repository) {
        this.repository = repository;
    }

    public List<ApartmentEntity> getAll(Long typeId, Long geolocationId) {
        Stream<ApartmentEntity> stream = repository.getAll().stream();

        if (!Objects.equals(typeId, 0L)) {
            stream = stream.filter(item -> item.getType().getId().equals(typeId));
        }
        if (!Objects.equals(geolocationId, 0L)) {
            stream = stream.filter(item -> item.getGeolocation().getId().equals(geolocationId));
        }

        return stream.toList();
    }

    public ApartmentEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public ApartmentEntity create(ApartmentEntity entity) {
        return repository.create(entity);
    }

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
        return repository.update(existsEntity);
    }

    public ApartmentEntity delete(Long id) {
        final ApartmentEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
