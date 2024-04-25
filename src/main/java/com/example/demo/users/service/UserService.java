package com.example.demo.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAll() {
        return repository.getAll();
    }

    public UserEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public UserEntity create(UserEntity entity) {
        return repository.create(entity);
    }

    public UserEntity update(Long id, UserEntity entity) {
        final UserEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.update(existsEntity);
    }

    public UserEntity delete(Long id) {
        final UserEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
