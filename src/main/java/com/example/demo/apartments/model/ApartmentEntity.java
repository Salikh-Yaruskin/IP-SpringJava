package com.example.demo.apartments.model;

import java.util.Objects;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.types.model.TypeEntity;
import com.example.demo.apartments.api.PropertyStatus;
import com.example.demo.geolocations.model.GeolocationEntity;

public class ApartmentEntity extends BaseEntity {
    private TypeEntity type;
    private PropertyStatus propertyStatus;
    private Boolean popular;
    private Double price;
    private String name;
    private String description;
    private GeolocationEntity geolocation;
    private Boolean shower;
    private Integer park;

    public ApartmentEntity() {
        super();
    }

    public ApartmentEntity(Long id, TypeEntity type,
            PropertyStatus propertyStatus, Boolean popular, Double price, String name,
            String description, GeolocationEntity geolocation, Boolean shower, Integer park) {
        super(id);
        this.type = type;
        this.propertyStatus = propertyStatus;
        this.popular = popular;
        this.price = price;
        this.name = name;
        this.description = description;
        this.geolocation = geolocation;
        this.shower = shower;
        this.park = park;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public PropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeolocationEntity getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(GeolocationEntity geolocation) {
        this.geolocation = geolocation;
    }

    public Boolean getShower() {
        return shower;
    }

    public void setShower(Boolean shower) {
        this.shower = shower;
    }

    public Integer getPark() {
        return park;
    }

    public void setPark(Integer park) {
        this.park = park;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, propertyStatus, popular, price, name, description, geolocation, shower, park);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final ApartmentEntity other = (ApartmentEntity) obj;
        return Objects.equals(other.getId(), id)
                && Objects.equals(other.getType(), type)
                && Objects.equals(other.getPropertyStatus(), propertyStatus)
                && Objects.equals(other.getPopular(), popular)
                && Objects.equals(other.getPrice(), price)
                && Objects.equals(other.getName(), name)
                && Objects.equals(other.getDescription(), description)
                && Objects.equals(other.getGeolocation(), geolocation)
                && Objects.equals(other.getShower(), shower)
                && Objects.equals(other.getPark(), park);
    }
}
