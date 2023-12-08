package com.example.demo.models;

import com.example.demo.serializers.LocalDateTimeDeserializer;
import com.example.demo.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;



public class UserPreferenceModel {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    private boolean showTrafficData;
    private boolean showTempData;
    private boolean showTrafficCamera;

    private Location location;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isShowTrafficData() {
        return showTrafficData;
    }

    public void setShowTrafficData(boolean showTrafficData) {
        this.showTrafficData = showTrafficData;
    }

    public boolean isShowTempData() {
        return showTempData;
    }

    public void setShowTempData(boolean showTempData) {
        this.showTempData = showTempData;
    }

    public boolean isShowTrafficCamera() {
        return showTrafficCamera;
    }

    public void setShowTrafficCamera(boolean showTrafficCamera) {
        this.showTrafficCamera = showTrafficCamera;
    }
}
