package com.example.demo.views.trafficCamera;

import com.example.demo.controllers.JsonReaderController;
import com.example.demo.controllers.TrafficCameraController;
import com.example.demo.models.Location;
import com.example.demo.services.LocationService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TrafficCameraView {

    private final VBox view = new VBox(10);

    public TrafficCameraView(LocationService locationService, TrafficCameraController trafficCameraController, TrafficCameraUpdater trafficCameraUpdater, TrafficImageView imageView, TrafficImageTitle trafficImageTitle, TrafficCameraSubtitle trafficCameraSubtitle) {

        Button updateButton = new TrafficImageUpdateButton();
        updateButton.setOnAction(e -> {
            trafficCameraUpdater.updateImage(imageView, trafficCameraSubtitle, locationService, trafficCameraController, locationService.selectedLocation);
        });

        updateImageFromUserPreferences(locationService, trafficCameraController, trafficCameraUpdater, imageView, trafficCameraSubtitle);
        view.setAlignment(Pos.TOP_CENTER);
        view.getChildren().addAll(
                trafficImageTitle,
                imageView,
                trafficCameraSubtitle,
                updateButton
        );
    }

    private static void updateImageFromUserPreferences(LocationService locationService, TrafficCameraController trafficCameraController, TrafficCameraUpdater trafficCameraUpdater, TrafficImageView imageView, TrafficCameraSubtitle trafficCameraSubtitle) {
        try {
            Location location = new JsonReaderController().readJsonData().getLocation();
            trafficCameraUpdater.updateImage(imageView, trafficCameraSubtitle, locationService, trafficCameraController, location);
        } catch (Exception e) {
//            System.out.println("Error: " + e);
        }
    }

    public VBox getView() {
        return view;
    }

}
