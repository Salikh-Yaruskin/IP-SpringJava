package com.example.demo.apartments.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ApartmentDto {
    private Long id;
    @NotNull
    @Min(1)
    private Long typeId;
    @NotNull
    @Min(1)
    private PropertyStatus propertyStatus;
    @NotNull
    @Min(1)
    private Boolean popular;
    @NotNull
    @Min(1)
    private Double price;
    @NotNull
    @Min(1)
    private String name;
    @NotNull
    @Min(1)
    private String description;
    @NotNull
    @Min(1)
    private Long geolocationId;
    @NotNull
    @Min(1)
    private Boolean shower;
    @NotNull
    @Min(1)
    private Integer park;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public Long getGeolocationId() {
        return geolocationId;
    }

    public void setGeolocationId(Long geolocationId) {
        this.geolocationId = geolocationId;
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
}
