package com.example.demo.views;

import com.example.demo.controllers.JsonReaderController;
import com.example.demo.controllers.JsonWriterController;
import com.example.demo.controllers.LocationController;
import com.example.demo.models.Location;
import com.example.demo.models.UserPreferenceModel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

// Ahmad
public class LocationView {

    public LocationController locationController = new LocationController();

    private VBox view = new VBox(10);

    private ComboBox<Location> comboBox = new ComboBox<>();

    public VBox getView() {
        return view;
    }
    public ComboBox<Location> getComboBox() {
        return comboBox;
    }

    public LocationView() {
        // Move this to LocationService.java
        comboBox.setItems(locationController.getLocations());
        comboBox.setValue(locationController.getDefaultLocation());
        comboBox.setOnAction(e -> {
            this.passSelectedLocation(comboBox.getValue());
        });
        this.view.getChildren().addAll(
                new Label("Location:"),
                comboBox
        );
    }

    private void passSelectedLocation(Location location) {
        locationController.passSelectedLocation(location);

    }
    public Location getSelectedLocation() {
        return locationController.getSelectedLocation();
    }
}
