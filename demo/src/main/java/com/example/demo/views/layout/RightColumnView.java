package com.example.demo.views.layout;

import com.example.demo.controllers.TrafficCameraController;
import com.example.demo.services.LocationService;
import com.example.demo.views.trafficCamera.*;
import javafx.scene.layout.VBox;

public class RightColumnView implements LayoutViews {

    public VBox getView() {
        LocationService locationService = LocationService.getInstance();
        TrafficCameraController trafficCameraController = TrafficCameraController.getInstance();
        TrafficCameraUpdater trafficCameraUpdater = new TrafficCameraUpdater();
        return new TrafficCameraView(locationService, trafficCameraController, trafficCameraUpdater, new TrafficImageView(trafficCameraController), new TrafficImageTitle(), new TrafficCameraSubtitle()).getView();
    }
}
