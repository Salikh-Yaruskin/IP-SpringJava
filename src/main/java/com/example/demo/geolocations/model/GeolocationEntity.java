package com.example.demo.geolocations.model;

import java.util.Objects;

import com.example.demo.core.model.BaseEntity;

public class GeolocationEntity extends BaseEntity {
    private String name;

    public GeolocationEntity() {
        super();
    }

    public GeolocationEntity(Long id, String name) {
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
        final GeolocationEntity other = (GeolocationEntity) obj;
        return Objects.equals(other.getId(), id)
                && Objects.equals(other.getName(), name);
    }
}
