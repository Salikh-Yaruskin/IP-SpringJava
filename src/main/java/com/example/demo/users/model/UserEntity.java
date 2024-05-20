package com.example.demo.users.model;

import java.util.List;
import java.util.Objects;

import com.example.demo.comments.model.CommentEntity;
import com.example.demo.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;
    private UserRole role;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final UserEntity other = (UserEntity) obj;
        return Objects.equals(other.getId(), getId())
                && Objects.equals(other.getName(), name)
                && Objects.equals(other.getEmail(), email)
                && Objects.equals(other.getPassword(), password)
                && Objects.equals(other.getRole(), role);
    }
}