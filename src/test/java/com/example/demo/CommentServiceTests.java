package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.comments.service.CommentService;
import com.example.demo.core.error.NotFoundException;
import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.users.model.UserEntity;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.apartments.api.PropertyStatus;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CommentServiceTests {
        @Autowired
        private CommentService commentService;

        @Test
        void getTest() {
                Assertions.assertThrows(NotFoundException.class, () -> commentService.get(0L));
        }

        @Test
        @Order(1)
        void createTest() {
                ApartmentEntity ae1 = new ApartmentEntity(null, null,
                                PropertyStatus.SALE, true, 122423.00,
                                "Red Dragon", "опр",
                                null, true, 3);
                UserEntity ue1 = new UserEntity(null, "admin", "admin@gmail.com", "admin");
                ApartmentEntity ae2 = new ApartmentEntity(null, null,
                                PropertyStatus.SALE, true, 12.00,
                                "Sahalin", "Cool very cool apartment",
                                null, true, 3);
                UserEntity ue2 = new UserEntity(null, "baron", "baron@gmail.com", "12345");
                commentService.create(new CommentEntity(null, ae1, ue1, "Digital disigne and very hipe house", null));
                commentService.create(
                                new CommentEntity(null, ae1, ue2, "Maybe is cool, but i don`t like this aria", null));
                final CommentEntity last = commentService
                                .create(new CommentEntity(null, ae2, ue1, "Okey, its okey", null));
                Assertions.assertEquals(3, commentService.getAll(0L, 0L).size());
                Assertions.assertEquals(last, commentService.get(3L));
        }

        @Test
        @Order(2)
        void updateTest() {
                final String testName = "TEST";
                final CommentEntity entity = commentService.get(3L);
                final String oldName = entity.getDescription();
                final CommentEntity newEntity = commentService.update(3L, new CommentEntity(null,
                                new ApartmentEntity(null, null,
                                                PropertyStatus.SALE, true, 122423.00,
                                                "dfdsfds sdf", "опр",
                                                null, true,
                                                3),
                                new UserEntity(null, "admin", "admin@gmail.com", "admin"), testName, null));
                Assertions.assertEquals(3, commentService.getAll(0L, 0L).size());
                Assertions.assertEquals(newEntity, commentService.get(3L));
                Assertions.assertEquals(testName, newEntity.getDescription());
                Assertions.assertNotEquals(oldName, newEntity.getDescription());
        }

        @Test
        @Order(3)
        void deleteTest() {
                commentService.delete(3L);
                Assertions.assertEquals(2, commentService.getAll(0L, 0L).size());
                final CommentEntity last = commentService.get(2L);
                Assertions.assertEquals(2L, last.getId());

                final CommentEntity newEntity = commentService.create(new CommentEntity(null,
                                new ApartmentEntity(null, new TypeEntity(null, "Однокомнатная"),
                                                PropertyStatus.SALE, true,
                                                122423.00,
                                                "dfdsfds sdf", "опр",
                                                new GeolocationEntity(null, "Ульяновск"), true, 3),
                                new UserEntity(null, "admin", "admin@gmail.com", "admin"), "Cool", null));
                Assertions.assertEquals(3, commentService.getAll(0L, 0L).size());
                Assertions.assertEquals(4L, newEntity.getId());
        }
}
