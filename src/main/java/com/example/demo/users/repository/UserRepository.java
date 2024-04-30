package com.example.demo.users.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.users.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByNameIgnoreCase(String name);
}