package com.example.demo.components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimePicker extends HBox {
    private final TextField timeTextField;

    public TextField getTimeTextField() {
        return timeTextField;
    }
    // Initialize properties for hours and minutes

    private final SimpleIntegerProperty hourProperty = new SimpleIntegerProperty(0);

    private final SimpleIntegerProperty minuteProperty = new SimpleIntegerProperty(0);

    public TimePicker() {
        // Create a TextField for time input
        timeTextField = new TextField("00:00");
        timeTextField.setPromptText("hh:mm");

        timeTextField.setMaxWidth(50);

        // Bind the TextField text to the properties
        timeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            parseTimeText(newValue);
        });

        // Add the TextField to the TimePicker
        getChildren().add(timeTextField);
    }

    public LocalTime getTime() {
        return LocalTime.of(hourProperty.get(), minuteProperty.get());
    }

    public void setTime(LocalTime time) {
        hourProperty.set(time.getHour());
        minuteProperty.set(time.getMinute());
        timeTextField.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void parseTimeText(String text) {
        try {
            LocalTime parsedTime = LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm"));
            hourProperty.set(parsedTime.getHour());
            minuteProperty.set(parsedTime.getMinute());
        } catch (DateTimeParseException e) {
            // Handle parsing error (invalid format)
            hourProperty.set(0);
            minuteProperty.set(0);
        }
    }

}
