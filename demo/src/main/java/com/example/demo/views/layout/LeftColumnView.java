package com.example.demo.views.layout;

import com.example.demo.controllers.JsonWriterController;
import com.example.demo.models.Location;
import com.example.demo.models.UserPreferenceModel;
import com.example.demo.views.CustomizeVisualizationView;
import com.example.demo.views.DateTimeView;
import com.example.demo.views.LocationView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LeftColumnView implements LayoutViews {

    private Button saveButton;
    private Button clearButton;
    private JsonWriterController jsonWriter = new JsonWriterController();

    public VBox getView() {
        return leftColumn;
    }

    // Create a VBox for the left column
    VBox leftColumn = new VBox(10);


    public LeftColumnView() {

        saveButton = new Button("Save Preferences");
        clearButton = new Button("Clear Preferences");

        // Apply the custom CSS style
        leftColumn.getStyleClass().add("custom-vbox-shadow");

        DateTimeView dateTimeView = new DateTimeView();

        LocationView locationView = new LocationView();

        CustomizeVisualizationView customizeVisualizationView = new CustomizeVisualizationView();


        leftColumn.getChildren().addAll(
                dateTimeView.getView(),
                locationView.getView(),
                customizeVisualizationView.getView(),
                saveButton,
                clearButton
        );
        // Handle save button click
        saveButton.setOnAction(e -> {

            saveUserPreferences(locationView, customizeVisualizationView, dateTimeView);
        });

        clearButton.setOnAction(e -> {
            locationView.getComboBox().setValue(new Location("Tampere", "23471", "C04507", "Tampere, Lakalaiva, Road 3", "23.769505", "61.462733", "439"));
            customizeVisualizationView.getTrafficSeriesVisibilityProperty().setValue(true);
            customizeVisualizationView.getTemperatureSeriesVisibilityProperty().setValue(true);
            customizeVisualizationView.getTrafficCameraViewVisibilityProperty().setValue(true);
            dateTimeView.getStartDateTimePicker().setDateTime(LocalDateTime.now().minus(7, ChronoUnit.DAYS));
        });
    }

    private void saveUserPreferences(LocationView locationView, CustomizeVisualizationView customizeVisualizationView, DateTimeView dateTimeView) {
        UserPreferenceModel data = new UserPreferenceModel();

        data.setLocation(locationView.getSelectedLocation());
        data.setShowTrafficData(customizeVisualizationView.getTrafficSeriesVisibilityProperty().getValue());
        data.setShowTempData(customizeVisualizationView.getTemperatureSeriesVisibilityProperty().getValue());
        data.setShowTrafficCamera(customizeVisualizationView.getTrafficCameraViewVisibilityProperty().getValue());
        data.setStartDate(dateTimeView.getStartDateTimePicker().getDateTime());
        jsonWriter.writeJsonData(data);
        System.out.println(locationView.getSelectedLocation());
        System.out.println(customizeVisualizationView.getTrafficSeriesVisibilityProperty().getValue());
        System.out.println(customizeVisualizationView.getTemperatureSeriesVisibilityProperty().getValue());
        System.out.println(customizeVisualizationView.getTrafficCameraViewVisibilityProperty().getValue());
    }
}
