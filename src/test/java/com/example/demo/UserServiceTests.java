package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    void getTest() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.get(0L));
    }

    @Test
    @Order(1)
    void createTest() {
        userService.create(new UserEntity(null, "admin", "admin@gmail.com", "admin"));
        userService.create(new UserEntity(null, "user1", "user1@gmail.com", "user1"));
        final UserEntity last = userService.create(new UserEntity(null, "user2", "user2@gmail.com", "user2"));
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertEquals(last, userService.get(3L));
    }

    @Test
    @Order(2)
    void updateTest() {
        final String test = "TEST";
        final UserEntity entity = userService.get(3L);
        final String oldName = entity.getName();
        final UserEntity newEntity = userService.update(3L, new UserEntity(1L, test, "user2@gmail.com", "user2"));
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertEquals(newEntity, userService.get(3L));
        Assertions.assertEquals(test, newEntity.getName());
        Assertions.assertNotEquals(oldName, newEntity.getName());
    }

    @Test
    @Order(3)
    void deleteTest() {
        userService.delete(3L);
        Assertions.assertEquals(2, userService.getAll().size());
        final UserEntity last = userService.get(2L);
        Assertions.assertEquals(2L, last.getId());

        final UserEntity newEntity = userService.create(new UserEntity(null, "admin", "admin", null));
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertEquals(4L, newEntity.getId());
    }
}
