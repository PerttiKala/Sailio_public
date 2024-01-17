package com.example.demo.dto;

import com.example.demo.models.Location;

import java.time.LocalDate;

public class LineChartFetchDto {

    Location location;
    LocalDate selectedDate;
    String latitude;
    String longitude;


    private LineChartFetchDto(Location location, LocalDate selectedDate) {
        this.location = location;
        this.selectedDate = selectedDate;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public static LineChartFetchDto createLineChartFetchDto(Location model, LocalDate selectedDate) {
        return new LineChartFetchDto(model, selectedDate);
    }

    public Location getLocation() {
        return location;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
