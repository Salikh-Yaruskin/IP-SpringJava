package com.example.demo.geolocations.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.configuration.Constants;
import com.example.demo.geolocations.model.GeolocationEntity;
import com.example.demo.geolocations.service.GeolocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/geolocation")
public class GeolocationContoller {
    private final GeolocationService geolocationService;
    private final ModelMapper modelMapper;

    public GeolocationContoller(GeolocationService geolocationService, ModelMapper modelMapper) {
        this.geolocationService = geolocationService;
        this.modelMapper = modelMapper;
    }

    private GeolocationDto toDto(GeolocationEntity entity) {
        return modelMapper.map(entity, GeolocationDto.class);
    }

    private GeolocationEntity toEntity(GeolocationDto dto) {
        return modelMapper.map(dto, GeolocationEntity.class);
    }

    @GetMapping
    public List<GeolocationDto> getAll() {
        return geolocationService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public GeolocationDto get(@PathVariable(name = "id") Long id) {
        return toDto(geolocationService.get(id));
    }

    @PostMapping
    public GeolocationDto create(@RequestBody @Valid GeolocationDto dto) {
        return toDto(geolocationService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public GeolocationDto update(
            @PathVariable(name = "id") Long id,
            @RequestBody GeolocationDto dto) {
        return toDto(geolocationService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public GeolocationDto delete(@PathVariable(name = "id") Long id) {
        return toDto(geolocationService.delete(id));
    }
}
