package com.example.demo;

import java.util.List;
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
import com.example.demo.users.service.UserService;
import com.example.demo.apartments.api.PropertyStatus;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	private final TypeService typeService;
	private final ApartmentService apartmentService;
	private final GeolocationService geolocationService;
	private final UserService userService;

	public DemoApplication(TypeService typeService, ApartmentService apartmentService,
			GeolocationService geolocationService, UserService userService) {
		this.typeService = typeService;
		this.apartmentService = apartmentService;
		this.geolocationService = geolocationService;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0 && Objects.equals("--populate", args[0])) {
			log.info("Create default types values");
			final var type1 = typeService.create(new TypeEntity("Однокомнатная"));
			final var type2 = typeService.create(new TypeEntity("Двухкомнатная"));
			final var type3 = typeService.create(new TypeEntity("Трехкомнатная"));

			log.info("Create default geolocations values");
			final var geolocation1 = geolocationService.create(new GeolocationEntity("Ульяновск"));
			final var geolocation2 = geolocationService.create(new GeolocationEntity("Москва"));
			final var geolocation3 = geolocationService.create(new GeolocationEntity("Санкт-Петербург"));

			log.info("Create default items values");
			apartmentService.create(new ApartmentEntity(type1, PropertyStatus.SALE, true, 122423.00,
					"dfdsfds sdf", "опр", geolocation1, true, 3));
			apartmentService.create(new ApartmentEntity(type2, PropertyStatus.SALE, true, 122423.00,
					"dfdsfds sdf", "опр", geolocation2, true, 3));
			apartmentService.create(new ApartmentEntity(type3, PropertyStatus.SALE, true, 122423.00,
					"dfdsfds sdf", "опр", geolocation3, true, 3));

			// log.info("Create default user values");
			// UserEntity userEntity1 = userService.create(new UserEntity("Админ",
			// "admin@mail.ru", "admin"));
			// UserEntity userEntity2 = userService.create(new UserEntity("qwer",
			// "qwer@mail.ru", "qwer"));
			// userService.create(new UserEntity("asdf", "asdf@mail.ru", "asdf"));
		}
	}
}
