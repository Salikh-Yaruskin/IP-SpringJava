package com.example.demo.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.users.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByNameIgnoreCase(String name);

    @Query("select comments from UserEntity d where d.id = ?1")
    List<CommentEntity> findByComment(Long id);
}