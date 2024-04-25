package com.example.demo.apartments.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.apartments.model.ApartmentEntity;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
    Optional<ApartmentEntity> findOneById(long id);

    List<ApartmentEntity> findByTypeIdAndGeolocationId(long typeId, long geolocationId);
}