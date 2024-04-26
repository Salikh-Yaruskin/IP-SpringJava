package com.example.demo.apartments.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.apartments.service.ApartmentService;
import com.example.demo.core.configuration.Constants;
import com.example.demo.types.service.TypeService;
import com.example.demo.geolocations.service.GeolocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;
    private final TypeService typeService;
    private final GeolocationService geolocationService;
    private final ModelMapper modelMapper;

    public ApartmentController(ApartmentService apartmentService, TypeService typeService,
            GeolocationService geolocationService, ModelMapper modelMapper) {
        this.apartmentService = apartmentService;
        this.typeService = typeService;
        this.geolocationService = geolocationService;
        this.modelMapper = modelMapper;
    }

    private ApartmentDto toDto(ApartmentEntity entity) {
        return modelMapper.map(entity, ApartmentDto.class);
    }

    private ApartmentEntity toEntity(ApartmentDto dto) {
        final ApartmentEntity entity = modelMapper.map(dto, ApartmentEntity.class);
        entity.setType(typeService.get(dto.getTypeId()));
        entity.setGeolocation(geolocationService.get(dto.getGeolocationId()));
        return entity;
    }

    @GetMapping
    public List<ApartmentDto> getAll(
            @RequestParam(name = "typeId", defaultValue = "0") Long typeId,
            @RequestParam(name = "geolocationId", defaultValue = "0") Long geolocationId) {
        return apartmentService.getAll(typeId, geolocationId).stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ApartmentDto get(
            @PathVariable(name = "id") Long id) {
        return toDto(apartmentService.get(id));
    }

    @PostMapping
    public ApartmentDto create(
            @RequestBody @Valid ApartmentDto dto) {
        return toDto(apartmentService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ApartmentDto update(
            @PathVariable(name = "id") Long id,
            @RequestBody ApartmentDto dto) {
        return toDto(apartmentService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ApartmentDto delete(
            @PathVariable(name = "id") Long id) {
        return toDto(apartmentService.delete(id));
    }
}
