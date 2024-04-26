package com.example.demo;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;

@SpringBootTest
class TypeServiceTests {
	@Autowired
	private TypeService typeService;
	private TypeEntity type;

	@BeforeEach
	void createData() {
		removeData();

		type = typeService.create(new TypeEntity("Однокомнатная"));
		typeService.create(new TypeEntity("Двухкомнатная"));
		typeService.create(new TypeEntity("Трехкомнатная"));
	}

	@AfterEach
	void removeData() {
		typeService.getAll().forEach(item -> typeService.delete(item.getId()));
	}

	@Test
	void getTest() {
		Assertions.assertThrows(NotFoundException.class, () -> typeService.get(0L));
	}

	@Test
	void createTest() {
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertEquals(type, typeService.get(type.getId()));
	}

	@Test
	void createNotUniqueTest() {
		final TypeEntity nonUniqueType = new TypeEntity("Однокомнатная");
		Assertions.assertThrows(IllegalArgumentException.class, () -> typeService.create(nonUniqueType));
	}

	@Test
	void createNullableTest() {
		final TypeEntity nullableType = new TypeEntity(null);
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> typeService.create(nullableType));
	}

	@Test
	void updateTest() {
		final String test = "TEST";
		final String oldName = type.getName();
		final TypeEntity newEntity = typeService.update(type.getId(), new TypeEntity(test));
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertEquals(newEntity, typeService.get(type.getId()));
		Assertions.assertEquals(test, newEntity.getName());
		Assertions.assertNotEquals(oldName, newEntity.getName());
	}

	@Test
	void deleteTest() {
		typeService.delete(type.getId());
		Assertions.assertEquals(2, typeService.getAll().size());

		final TypeEntity newEntity = typeService.create(new TypeEntity(type.getName()));
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertNotEquals(type.getId(), newEntity.getId());
	}
}
