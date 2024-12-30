package com.danidinchev.carmanagement.dto;

public class GarageDailyAvailabilityReportDTO {
    private String date;
    private int requests;
    private int availableCapacity;

    public GarageDailyAvailabilityReportDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public GarageDailyAvailabilityReportDTO(String date, int requests, int availableCapacity) {
        this.date = date;
        this.requests = requests;
        this.availableCapacity = availableCapacity;
    }
}
