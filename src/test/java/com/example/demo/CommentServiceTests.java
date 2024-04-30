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

import com.example.demo.apartments.api.PropertyStatus;
import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.service.ApartmentService;
import com.example.demo.comments.model.CommentEntity;
import com.example.demo.comments.service.CommentService;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CommentServiceTests {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private GeolocationService geolocationService;
    @Autowired
    private TypeService typeService;

    private CommentEntity commentEntity1;
    private CommentEntity commentEntity2;
    private CommentEntity commentEntity3;

    private UserEntity userEntity1;
    private UserEntity userEntity2;

    private GeolocationEntity geolocationEntity;
    private TypeEntity typeEntity;

    private ApartmentEntity apartmentEntity1;

    @BeforeEach
    void createData() {
        removeData();

        userEntity1 = userService.create(new UserEntity("first", "mail", "12345"));
        userEntity2 = userService.create(new UserEntity("second", "gmail", "12345131"));

        geolocationEntity = geolocationService.create(new GeolocationEntity("Ульяновск"));
        typeEntity = typeService.create(new TypeEntity("Однокомнатная"));

        apartmentEntity1 = apartmentService.create(new ApartmentEntity(
                typeEntity, PropertyStatus.SALE, true, 122423.00,
                "dfdsfds sdf", "опр", geolocationEntity, true, 3));

        commentEntity1 = commentService.create(new CommentEntity("dafddfsf", null, userEntity1, apartmentEntity1));
        commentEntity2 = commentService.create(new CommentEntity("dfdsd", null, userEntity2, apartmentEntity1));
        commentEntity3 = commentService.create(new CommentEntity("dfsfds", null, userEntity1, apartmentEntity1));
    }

    @AfterEach
    void removeData() {
        commentService.getAlll().forEach(comment -> commentService.delete(comment.getId()));
        userService.getAll().forEach(user -> userService.delete(user.getId()));
        apartmentService.getAlll().forEach(apartament -> apartmentService.delete(apartament.getId()));
        geolocationService.getAll().forEach(geolocation -> geolocationService.delete(geolocation.getId()));
        typeService.getAll().forEach(type -> typeService.delete(type.getId()));
    }

    @Test
    @Order(1)
    void createTest() {
        Assertions.assertEquals(2, commentService.getAll(userEntity1.getId(), apartmentEntity1.getId()).size());
        Assertions.assertEquals(3, commentService.getAll(0L, 0L).size());
    }

    @Test
    @Order(2)
    void orderFilterTest() {
        Assertions.assertEquals(2, commentService.getAll(userEntity1.getId(), apartmentEntity1.getId()).size());
        Assertions.assertEquals(1, commentService.getAll(userEntity2.getId(), apartmentEntity1.getId()).size());
    }

    @Test
    @Order(3)
    void deleteTest() {
        commentService.delete(commentEntity1.getId());
        Assertions.assertEquals(2, commentService.getAll(0L, 0L).size());

        final CommentEntity last = commentService.get(commentEntity2.getId());
        Assertions.assertEquals(8, last.getId());

        final CommentEntity newComment = commentService
                .create(new CommentEntity("fdfsfds", null, userEntity2, apartmentEntity1));
        Assertions.assertEquals(3, commentService.getAll(0L, 0L).size());
        Assertions.assertEquals(10L, newComment.getId());
    }
}
