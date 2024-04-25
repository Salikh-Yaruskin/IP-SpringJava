package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GeolocationServiceTests {
    @Autowired
    private GeolocationService geolocationService;

    @Test
    void getTest() {
        Assertions.assertThrows(NotFoundException.class, () -> geolocationService.get(0L));
    }

    @Test
    @Order(1)
    void createTest() {
        geolocationService.create(new GeolocationEntity(null, "Ульяновск"));
        geolocationService.create(new GeolocationEntity(null, "Москва"));
        final GeolocationEntity last = geolocationService.create(new GeolocationEntity(null, "Санкт-Петербург"));
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertEquals(last, geolocationService.get(3L));
    }

    @Test
    @Order(2)
    void updateTest() {
        final String test = "TEST";
        final GeolocationEntity entity = geolocationService.get(3L);
        final String oldName = entity.getName();
        final GeolocationEntity newEntity = geolocationService.update(3L, new GeolocationEntity(1L, test));
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertEquals(newEntity, geolocationService.get(3L));
        Assertions.assertEquals(test, newEntity.getName());
        Assertions.assertNotEquals(oldName, newEntity.getName());
    }

    @Test
    @Order(3)
    void deleteTest() {
        geolocationService.delete(3L);
        Assertions.assertEquals(2, geolocationService.getAll().size());
        final GeolocationEntity last = geolocationService.get(2L);
        Assertions.assertEquals(2L, last.getId());

        final GeolocationEntity newEntity = geolocationService.create(new GeolocationEntity(null, "Санкт-Петербург"));
        Assertions.assertEquals(3, geolocationService.getAll().size());
        Assertions.assertEquals(4L, newEntity.getId());
    }
}
