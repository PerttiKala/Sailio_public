package com.example.demo.controllers;

import com.example.demo.services.TrafficCameraService;
import com.example.demo.models.Location;
import javafx.scene.image.Image;
import javafx.beans.property.BooleanProperty;


public class TrafficCameraController {
    private static final TrafficCameraController instance = new TrafficCameraController();
    private TrafficCameraService trafficCameraServices = new TrafficCameraService();
    public static TrafficCameraController getInstance() {
        return instance;
    }

    public Image getTrafficImage(Location location) {
        return trafficCameraServices.getTrafficImage(location);
    }

    public void toggleImageVisibility() {
        trafficCameraServices.toggleImageVisibility();
    }

    public BooleanProperty getTrafficCameraViewVisibilityProperty() {
        return trafficCameraServices.getTrafficCameraViewVisibilityProperty();
    }
}
