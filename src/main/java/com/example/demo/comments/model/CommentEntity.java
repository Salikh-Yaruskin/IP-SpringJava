package com.example.demo.comments.model;

import java.util.Objects;

import java.util.Date;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.apartments.model.ApartmentEntity;
import com.example.demo.users.model.UserEntity;

public class CommentEntity extends BaseEntity {
    private ApartmentEntity apartment;
    private UserEntity user;
    private String description;
    private Date date;

    public CommentEntity() {
        super();
    }

    public CommentEntity(Long id, ApartmentEntity apartment, UserEntity user, String description, Date date) {
        super(id);
        this.apartment = apartment;
        this.user = user;
        this.description = description;
        this.date = date;
    }

    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentEntity apartment) {
        this.apartment = apartment;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apartment, user, description, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final CommentEntity other = (CommentEntity) obj;
        return Objects.equals(other.getId(), id)
                && Objects.equals(other.getApartment(), apartment)
                && Objects.equals(other.getUser(), user)
                && Objects.equals(other.getDescription(), description)
                && Objects.equals(other.getDate(), date);
    }
}
