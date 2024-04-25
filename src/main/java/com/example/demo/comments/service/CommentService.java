package com.example.demo.comments.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.comments.repository.CommentRepository;
import com.example.demo.core.error.NotFoundException;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<CommentEntity> getAll(Long apartmentId, Long userId) {
        Stream<CommentEntity> stream = repository.getAll().stream();

        if (!Objects.equals(apartmentId, 0L)) {
            stream = stream.filter(item -> item.getApartment().getId().equals(apartmentId));
        }
        if (apartmentId == null || apartmentId < 0) {
            throw new IllegalArgumentException("Apartment ID must be greater than 0.");
        }
        if (!Objects.equals(userId, 0L)) {
            stream = stream.filter(item -> item.getUser().getId().equals(userId));
        }
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }
        return stream.toList();
    }

    public CommentEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public CommentEntity create(CommentEntity entity) {
        return repository.create(entity);
    }

    public CommentEntity update(Long id, CommentEntity entity) {
        final CommentEntity existsEntity = get(id);
        existsEntity.setApartment(entity.getApartment());
        existsEntity.setUser(entity.getUser());
        existsEntity.setDescription(entity.getDescription());
        existsEntity.setDate(entity.getDate());
        return repository.update(existsEntity);
    }

    public CommentEntity delete(Long id) {
        final CommentEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
