package com.example.demo.services;

import com.example.demo.data.retriever.TrafficCameraFetcher;
import com.example.demo.models.CameraModel;
import com.example.demo.models.Location;
import javafx.scene.image.Image;
import javafx.beans.property.BooleanProperty;

public class TrafficCameraService {

    TrafficCameraFetcher trafficCameraFetcher = new TrafficCameraFetcher();
    CameraModel cameraModel = new CameraModel();

    public Image getTrafficImage(Location location) {
        return trafficCameraFetcher.fetchCameraImage(location);
    }

    public BooleanProperty getTrafficCameraViewVisibilityProperty() {
        return cameraModel.getTrafficCameraViewVisibilityProperty();
    }

    public void toggleImageVisibility() {
        cameraModel.toggleImageVisibility();
    }

}
