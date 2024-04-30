package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

@SpringBootTest
class UserServiceTests {
    @Autowired
    private UserService userService;
    private UserEntity user;

    @BeforeEach
    void createData() {
        removeData();

        user = userService.create(new UserEntity("Админ", "admin@mail.ru", "admin"));
        userService.create(new UserEntity("qwer", "qwer@mail.ru", "qwer"));
        userService.create(new UserEntity("asdf", "asdf@mail.ru", "asdf"));
    }

    @AfterEach
    void removeData() {
        userService.getAll().forEach(item -> userService.delete(item.getId()));
    }

    @Test
    void getTest() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.get(0L));
    }

    @Test
    void createTest() {
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertEquals(user, userService.get(user.getId()));
    }

    @Test
    void createNotUniqueTest() {
        final UserEntity nonUniqueType = new UserEntity("Админ", "admin@mail.ru", "admin");
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.create(nonUniqueType));
    }

    @Test
    void createNullableTest() {
        final UserEntity nullableType = new UserEntity(null, null, null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.create(nullableType));
    }

    @Test
    void updateTest() {
        final String test = "TEST";
        final String oldName = user.getName();
        final String oldEmail = user.getEmail();
        final String oldPass = user.getPassword();
        final UserEntity newEntity = userService.update(user.getId(), new UserEntity(test, oldEmail, oldPass));
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertEquals(newEntity, userService.get(user.getId()));
        Assertions.assertEquals(test, newEntity.getName());
        Assertions.assertNotEquals(oldName, newEntity.getName());
    }

    @Test
    void deleteTest() {
        userService.delete(user.getId());
        Assertions.assertEquals(2, userService.getAll().size());

        final UserEntity newEntity = userService
                .create(new UserEntity(user.getName(), user.getEmail(), user.getPassword()));
        Assertions.assertEquals(3, userService.getAll().size());
        Assertions.assertNotEquals(user.getId(), newEntity.getId());
    }
}
