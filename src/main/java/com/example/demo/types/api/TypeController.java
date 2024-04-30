package com.example.demo.types.api;

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
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.service.TypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/type")
public class TypeController {
    private final TypeService typeService;
    private final ModelMapper modelMapper;

    public TypeController(TypeService typeService, ModelMapper modelMapper) {
        this.typeService = typeService;
        this.modelMapper = modelMapper;
    }

    private TypeDto toDto(TypeEntity entity) {
        return modelMapper.map(entity, TypeDto.class);
    }

    private TypeEntity toEntity(TypeDto dto) {
        return modelMapper.map(dto, TypeEntity.class);
    }

    @GetMapping
    public List<TypeDto> getAll() {
        return typeService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public TypeDto get(@PathVariable(name = "id") Long id) {
        return toDto(typeService.get(id));
    }

    @PostMapping
    public TypeDto create(@RequestBody @Valid TypeDto dto) {
        return toDto(typeService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public TypeDto update(
            @PathVariable(name = "id") Long id,
            @RequestBody TypeDto dto) {
        return toDto(typeService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public TypeDto delete(@PathVariable(name = "id") Long id) {
        return toDto(typeService.delete(id));
    }
}
