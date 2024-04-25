package com.example.demo.types.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.types.repository.TypeRepository;

@Service
public class TypeService {
    private final TypeRepository repository;

    public TypeService(TypeRepository repository) {
        this.repository = repository;
    }

    public List<TypeEntity> getAll() {
        return repository.getAll();
    }

    public TypeEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public TypeEntity create(TypeEntity entity) {
        return repository.create(entity);
    }

    public TypeEntity update(Long id, TypeEntity entity) {
        final TypeEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.update(existsEntity);
    }

    public TypeEntity delete(Long id) {
        final TypeEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
