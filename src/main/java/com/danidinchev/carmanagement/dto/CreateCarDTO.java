package com.danidinchev.carmanagement.dto;

import java.util.List;

public class CreateCarDTO {

    private Long id;
    private String make;
    private String model;
    private int productionYear;
    private String licensePlate;
    private List<Long> garageIds;

    public List<Long> getGarageIds() {
        return garageIds;
    }


    public void setGarageIds(List<Long> garageIds) {
        this.garageIds = garageIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
