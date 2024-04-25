package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TypeServiceTests {
	@Autowired
	private TypeService typeService;

	@Test
	void getTest() {
		Assertions.assertThrows(NotFoundException.class, () -> typeService.get(0L));
	}

	@Test
	@Order(1)
	void createTest() {
		typeService.create(new TypeEntity(null, "Однокомнатная"));
		typeService.create(new TypeEntity(null, "Двухкомнатная"));
		final TypeEntity last = typeService.create(new TypeEntity(null, "Трехкомнатная"));
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertEquals(last, typeService.get(3L));
	}

	@Test
	@Order(2)
	void updateTest() {
		final String test = "TEST";
		final TypeEntity entity = typeService.get(3L);
		final String oldName = entity.getName();
		final TypeEntity newEntity = typeService.update(3L, new TypeEntity(1L, test));
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertEquals(newEntity, typeService.get(3L));
		Assertions.assertEquals(test, newEntity.getName());
		Assertions.assertNotEquals(oldName, newEntity.getName());
	}

	@Test
	@Order(3)
	void deleteTest() {
		typeService.delete(3L);
		Assertions.assertEquals(2, typeService.getAll().size());
		final TypeEntity last = typeService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final TypeEntity newEntity = typeService.create(new TypeEntity(null, "Двухкомнатная"));
		Assertions.assertEquals(3, typeService.getAll().size());
		Assertions.assertEquals(4L, newEntity.getId());
	}
}
