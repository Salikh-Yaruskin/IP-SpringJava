package com.example.demo;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;
import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.service.ApartmentService;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;
import com.example.demo.comments.model.CommentEntity;
import com.example.demo.comments.service.CommentService;
import com.example.demo.apartments.api.PropertyStatus;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	private final TypeService typeService;
	private final ApartmentService itemService;
	private final GeolocationService geolocationService;
	private final UserService userService;
	private final CommentService commentService;

	public DemoApplication(TypeService typeService, ApartmentService itemService,
			GeolocationService geolocationService, UserService userService, CommentService commentService) {
		this.typeService = typeService;
		this.itemService = itemService;
		this.geolocationService = geolocationService;
		this.userService = userService;
		this.commentService = commentService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0 && Objects.equals("--populate", args[0])) {
			log.info("Create default types values");
			final var type1 = typeService.create(new TypeEntity(null, "Однокомнатная"));
			final var type2 = typeService.create(new TypeEntity(null, "Двухкомнатная"));
			final var type3 = typeService.create(new TypeEntity(null, "Трехкомнатная"));

			log.info("Create default geolocations values");
			final var geolocation1 = geolocationService.create(new GeolocationEntity(null, "Ульяновск"));
			final var geolocation2 = geolocationService.create(new GeolocationEntity(null, "Москва"));
			final var geolocation3 = geolocationService.create(new GeolocationEntity(null, "Санкт-Петербург"));
			final var geolocation4 = geolocationService.create(new GeolocationEntity(null, "Сочи"));

			log.info("Create default user values");
			final var user1 = userService.create(new UserEntity(null, "admin", "admin@gmail.com", "admin"));
			final var user2 = userService.create(new UserEntity(null, "Alexei", "alexei@gmail.com", "12345"));

			log.info("Create default items values");
			final var apartment1 = itemService.create(
					new ApartmentEntity(null, type1,
							PropertyStatus.SALE, true, 17500000.00, "Тайский дракон", "опр",
							geolocation1, true, 2));
			final var apartment2 = itemService
					.create(new ApartmentEntity(null, type2,
							PropertyStatus.SALE, true, 122423.00, "dfdsfds sdf", "опр",
							geolocation2, true, 3));
			itemService.create(new ApartmentEntity(null, type3,
					PropertyStatus.SALE, true, 1111111.00, "sdc1 дракон", "опр",
					geolocation3, true, 5));
			itemService.create(
					new ApartmentEntity(null, type3,
							PropertyStatus.SALE, true, 1234567788.00, "Снек За Бу Горский", "опр",
							geolocation4, true, 5));

			log.info("Create default comment values");
			commentService.create(new CommentEntity(null, apartment1, user1, "Класс класс класс", null));
			commentService
					.create(new CommentEntity(null, apartment1, user1, "Живу пол года, изменений не видно", null));
			commentService.create(new CommentEntity(null, apartment2, user1, "Прошлая квартира была лучше", null));
			commentService.create(new CommentEntity(null, apartment2, user2, "Необычно", null));
		}
	}
}
