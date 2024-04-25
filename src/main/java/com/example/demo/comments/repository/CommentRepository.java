package com.example.demo.comments.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.core.repository.MapRepository;

@Repository
public class CommentRepository extends MapRepository<CommentEntity> {
}
