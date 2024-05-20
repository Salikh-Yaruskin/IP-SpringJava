package com.example.demo.users.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.core.error.NotFoundException;
import com.example.demo.core.security.UserPrincipal;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.model.UserRole;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.core.configuration.Constants;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    private void checkName(Long id, String name) {
        final Optional<UserEntity> existsUser = repository.findByNameIgnoreCase(name);
        if (existsUser.isPresent() && !existsUser.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("User with login %s is already exists", name));
        }
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public List<CommentEntity> getCommentsByUsers(Long id) {
        return repository.findByComment(id);
    }

    @Transactional(readOnly = true)
    public Long getCommentCountByUserId(Long id) {
        return repository.countCommentsByUserId(id);
    }

    @Transactional(readOnly = true)
    public UserEntity get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(UserEntity.class, id));
    }

    @Transactional(readOnly = true)
    public UserEntity getByLogin(String name) {
        return repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid name"));
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        checkName(null, entity.getName());
        final String password = Optional.ofNullable(entity.getPassword()).orElse("");
        entity.setPassword(
                passwordEncoder.encode(
                        StringUtils.hasText(password.strip()) ? password : Constants.DEFAULT_PASSWORD));
        entity.setRole(Optional.ofNullable(entity.getRole()).orElse(UserRole.USER));
        return repository.save(entity);
    }

    @Transactional
    public UserEntity update(long id, UserEntity entity) {
        final UserEntity existsEntity = get(id);
        checkName(id, entity.getName());
        existsEntity.setName(entity.getName());
        repository.save(existsEntity);
        return existsEntity;
    }

    @Transactional
    public UserEntity delete(long id) {
        final UserEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity existsUser = getByLogin(username);
        return new UserPrincipal(existsUser);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getUsersOrderedByCommentCount() {
        return repository.findUsersOrderedByCommentCount();
    }
}