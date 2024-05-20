package com.example.demo.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.users.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByNameIgnoreCase(String name);

    @Query("select comments from UserEntity d where d.id = ?1")
    List<CommentEntity> findByComment(Long id);

    @Query("select count(c) from CommentEntity c where c.user.id = ?1")
    Long countCommentsByUserId(Long userId);

    @Query("select u from UserEntity u left join u.comments c group by u.id order by count(c) desc")
    List<UserEntity> findUsersOrderedByCommentCount();
}