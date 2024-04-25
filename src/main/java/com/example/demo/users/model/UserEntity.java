package com.example.demo.users.model;

import java.util.Objects;

import com.example.demo.core.model.BaseEntity;

public class UserEntity extends BaseEntity {
    private String name;
    private String email;
    private String password;

    public UserEntity() {
        super();
    }

    public UserEntity(Long id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final UserEntity other = (UserEntity) obj;
        return Objects.equals(other.getId(), id)
                && Objects.equals(other.getName(), name)
                && Objects.equals(other.getName(), email)
                && Objects.equals(other.getName(), password);
    }
}
