package com.example.demo.types.model;

import java.util.Objects;

import com.example.demo.core.model.BaseEntity;

public class TypeEntity extends BaseEntity {
    private String name;

    public TypeEntity() {
        super();
    }

    public TypeEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final TypeEntity other = (TypeEntity) obj;
        return Objects.equals(other.getId(), id)
                && Objects.equals(other.getName(), name);
    }

}
