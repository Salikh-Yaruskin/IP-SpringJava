package com.example.demo.apartments.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.apartments.model.ApartmentEntity;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
    List<ApartmentEntity> findByTypeIdAndGeolocationId(long typeId, long geolocationId);
}