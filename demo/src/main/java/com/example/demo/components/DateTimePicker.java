package com.example.demo.components;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimePicker {

    private final Label dateTimeLabel;
    private final HBox hbox = new HBox();
    private final String dateTimeLabelText;

    public VBox getLayout() {
        return vbox;
    }

    private final VBox vbox;

    // Create DatePicker for selecting a date
    DatePicker datePicker = new DatePicker();

    // Create TimePicker for selecting a time
    TimePicker timePicker = new TimePicker();

    public LocalDateTime getDateTime() {
        LocalDate selectedDate = datePicker.getValue();
        LocalTime selectedTime = timePicker.getTime();
        return LocalDateTime.of(selectedDate, selectedTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        datePicker.setValue(dateTime.toLocalDate());
        timePicker.setTime(dateTime.toLocalTime());
    }

    public DateTimePicker(String headerText, String dateTimeLabelText, LocalDate defaultDate) {

        //Setting the space between the nodes of a HBox pane
        hbox.setSpacing(10);


        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                // set max date to datetimepicker to be minus 7 days from today
                LocalDate maxDate = today.minusDays(7);
                setDisable(empty || date.isAfter(maxDate));
            }
        });

        datePicker.setValue(defaultDate); // Set the initial date to the current date
        datePicker.setMaxWidth(100);
        // Create a label to display the selected DateTime
        this.dateTimeLabelText = dateTimeLabelText;
        dateTimeLabel = new Label(this.dateTimeLabelText);

        hbox.getChildren().addAll(
                datePicker
        );

        // Create a VBox layout to arrange the controls
        // 10 pixels spacing between nodes
        vbox = new VBox(10);
        vbox.getChildren().addAll(
                new Label(headerText),
                hbox,
                dateTimeLabel
        );

    }

    // Update the label with the selected DateTime
    private void updateDateTimeLabel(DatePicker datePicker, TimePicker timePicker, Label label) {
        LocalDate selectedDate = datePicker.getValue();
        label.setText(this.dateTimeLabelText + selectedDate.format(DateTimeFormatter.ofPattern("d.M.yyyy")));
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }
}
