package com.example.demo.views.trafficCamera;

import com.example.demo.controllers.TrafficCameraController;
import com.example.demo.models.Location;
import com.example.demo.services.LocationService;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

// Separate class for updating image and UI content
public class TrafficCameraUpdater {
    public void updateImage(ImageView cameraImage, Label cameraSubtitle, LocationService locationService, TrafficCameraController trafficCameraController, Location location) {
        // Update image and UI content based on the provided location
        cameraImage.setImage(trafficCameraController.getTrafficImage(locationService.selectedLocation));
        cameraSubtitle.setText(location.getWeatherStationName());
    }
}
