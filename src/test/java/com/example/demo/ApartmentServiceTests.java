package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.api.PropertyStatus;
import com.example.demo.apartments.service.ApartmentService;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;

import jakarta.persistence.EntityManager;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ApartmentServiceTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private GeolocationService geolocationService;

    private TypeEntity type1;
    private TypeEntity type2;
    private TypeEntity type3;

    private GeolocationEntity geolocation1;
    private GeolocationEntity geolocation2;
    private GeolocationEntity geolocation3;

    @BeforeEach
    void createData() {
        removeData();

        type1 = typeService.create(new TypeEntity("Однокомнатная"));
        type2 = typeService.create(new TypeEntity("Двухкомнатная"));
        type3 = typeService.create(new TypeEntity("Трехкомнатная"));

        geolocation1 = geolocationService.create(new GeolocationEntity("Ульяновск"));
        geolocation2 = geolocationService.create(new GeolocationEntity("Москва"));
        geolocation3 = geolocationService.create(new GeolocationEntity("Санкт-Петербург"));

        final var apartments = List.of(
                new ApartmentEntity(type1, PropertyStatus.SALE, true, 122423.00,
                        "dfdsfds sdf", "опр", geolocation1, true, 3),
                new ApartmentEntity(type2, PropertyStatus.SALE, true, 122423.00,
                        "dfdsfds sdf", "опр", geolocation2, true, 3),
                new ApartmentEntity(type3, PropertyStatus.SALE, true, 122423.00,
                        "dfdsfds sdf", "опр", geolocation3, true, 3));
        apartments.forEach(apartment -> apartmentService.create(apartment));
    }

    @AfterEach
    void removeData() {
        apartmentService.getAll(type1.getId(), geolocation1.getId())
                .forEach(apartment -> apartmentService.delete(apartment.getId()));
        typeService.getAll().forEach(type -> typeService.delete(type.getId()));
        geolocationService.getAll().forEach(geolocation -> geolocationService.delete(geolocation.getId()));
    }

    @Test
    @Order(1)
    void createTest() {
        Assertions.assertEquals(3, apartmentService.getAll(0L, 0L).size());
        Assertions.assertEquals(0, apartmentService.getAll(0L, 0L).size());
    }

    @Test
    @Order(2)
    void orderFilterTest() {
        Assertions.assertEquals(2, apartmentService.getAll(type1.getId(), geolocation1.getId()).size());
        Assertions.assertEquals(3, apartmentService.getAll(type1.getId(), geolocation2.getId()).size());
        Assertions.assertEquals(2, apartmentService.getAll(type1.getId(), geolocation3.getId()).size());
    }
}
