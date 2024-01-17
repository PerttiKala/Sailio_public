package com.example.demo.views;

import com.example.demo.components.DateTimePicker;
import com.example.demo.controllers.DateTimeController;
import javafx.scene.layout.HBox;

public class DateTimeView {
    private HBox dateTimeRangeLayout = new HBox(10);
    DateTimeController dateTimeController = DateTimeController.getInstance();

    DateTimePicker startDateTimePicker;

    public DateTimeView() {

        startDateTimePicker = new DateTimePicker("Start date", "", dateTimeController.getStartDate());

        startDateTimePicker.getDatePicker().setOnAction(e -> dateTimeController.setStartDate(startDateTimePicker.getDatePicker(), startDateTimePicker.getTimePicker()));

        dateTimeRangeLayout.getChildren().addAll(
                startDateTimePicker.getLayout()
        );
    }

    public HBox getView() {
        return dateTimeRangeLayout;
    }

    public DateTimePicker getStartDateTimePicker() {
        return startDateTimePicker;
    }
}
