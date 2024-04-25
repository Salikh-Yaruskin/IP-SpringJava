package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.api.PropertyStatus;
import com.example.demo.apartments.service.ApartmentService;
import com.example.demo.core.error.NotFoundException;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.geolocations.model.GeolocationEntity;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ApartmentServiceTests {
    @Autowired
    private ApartmentService lineService;

    @Test
    void getTest() {
        Assertions.assertThrows(NotFoundException.class, () -> lineService.get(0L));
    }

    @Test
    @Order(1)
    void createTest() {
        lineService.create(new ApartmentEntity(null, new TypeEntity(null, "Однокомнатная"),
                PropertyStatus.SALE, true, 122423.00,
                "dfdsfds sdf", "опр",
                new GeolocationEntity(null, "Ульяновск"), true, 3));
        lineService.create(new ApartmentEntity(null, new TypeEntity(null, "Двухкомнатная"),
                PropertyStatus.SALE, true, 122423.00,
                "dfdsfds sdf", "опр",
                new GeolocationEntity(null, "Москва"), true, 3));
        final ApartmentEntity last = lineService
                .create(new ApartmentEntity(null, new TypeEntity(null, "Трехкомнатная"),
                        PropertyStatus.SALE, true, 122423.00,
                        "dfdsfds sdf", "опр",
                        new GeolocationEntity(null, "Сочи"), true, 3));
        Assertions.assertEquals(3, lineService.getAll(0L, 0L).size());
        Assertions.assertEquals(last, lineService.get(3L));
    }

    @Test
    @Order(2)
    void updateTest() {
        final String testName = "TEST";
        final ApartmentEntity entity = lineService.get(3L);
        final String oldName = entity.getName();
        final ApartmentEntity newEntity = lineService.update(3L,
                new ApartmentEntity(1L, new TypeEntity(null, "name"),
                        PropertyStatus.SALE, true, 0.00,
                        testName, "description",
                        new GeolocationEntity(null, "name"), true, 3));
        Assertions.assertEquals(3, lineService.getAll(0L, 0L).size());
        Assertions.assertEquals(newEntity, lineService.get(3L));
        Assertions.assertEquals(testName, newEntity.getName());
        Assertions.assertNotEquals(oldName, newEntity.getName());
    }

    @Test
    @Order(3)
    void deleteTest() {
        lineService.delete(3L);
        Assertions.assertEquals(2, lineService.getAll(0L, 0L).size());
        final ApartmentEntity last = lineService.get(2L);
        Assertions.assertEquals(2L, last.getId());

        final ApartmentEntity newEntity = lineService
                .create(new ApartmentEntity(null, new TypeEntity(null, "name"),
                        PropertyStatus.SALE, true, 122423.00,
                        "name", "опр",
                        new GeolocationEntity(null, "name"), true, 3));
        Assertions.assertEquals(3, lineService.getAll(0L, 0L).size());
        Assertions.assertEquals(4L, newEntity.getId());
    }
}
