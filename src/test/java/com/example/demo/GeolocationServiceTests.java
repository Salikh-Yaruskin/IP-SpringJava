package com.example.demo;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GeolocationServiceTests {
    @Autowired
    private GeolocationService geolocationService;
    private GeolocationEntity geolocation;

    @BeforeEach
    void createData() {
        removeData();

        geolocation = geolocationService.create(new GeolocationEntity("Ульяновск"));
        geolocationService.create(new GeolocationEntity("Москва"));
        geolocationService.create(new GeolocationEntity("Санкт-Петербург"));
    }

    @AfterEach
    void removeData() {
        geolocationService.getAll().forEach(item -> geolocationService.delete(item.getId()));
    }

    @Test
    void getTest() {
        Assertions.assertThrows(NotFoundException.class, () -> geolocationService.get(0L));
    }

    @Test
    void createTest() {
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertEquals(geolocation, geolocationService.get(geolocation.getId()));
    }

    @Test
    void createNotUniqueTest() {
        final GeolocationEntity nonUniqueType = new GeolocationEntity("Ульяновск");
        Assertions.assertThrows(IllegalArgumentException.class, () -> geolocationService.create(nonUniqueType));
    }

    @Test
    void createNullableTest() {
        final GeolocationEntity nullableType = new GeolocationEntity(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> geolocationService.create(nullableType));
    }

    @Test
    void updateTest() {
        final String test = "TEST";
        final String oldName = geolocation.getName();
        final GeolocationEntity newEntity = geolocationService.update(geolocation.getId(), new GeolocationEntity(test));
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertEquals(newEntity, geolocationService.get(geolocation.getId()));
        Assertions.assertEquals(test, newEntity.getName());
        Assertions.assertNotEquals(oldName, newEntity.getName());
    }

    @Test
    void deleteTest() {
        geolocationService.delete(geolocation.getId());
        Assertions.assertEquals(2, geolocationService.getAll().size());

        final GeolocationEntity newEntity = geolocationService.create(new GeolocationEntity(geolocation.getName()));
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertNotEquals(geolocation.getId(), newEntity.getId());
    }
}
