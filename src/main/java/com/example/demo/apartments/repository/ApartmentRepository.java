package com.example.demo.apartments.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.apartments.model.ApartmentEntity;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
    Optional<ApartmentEntity> findByNameIgnoreCase(String name);

    List<ApartmentEntity> findByTypeIdAndGeolocationId(long typeId, long geolocationId);

    @Query("select a from ApartmentEntity a left join a.comments c group by a.id order by count(c) desc")
    Page<ApartmentEntity> findApartamentsOrderedByCommentCount(Pageable pageable);
}