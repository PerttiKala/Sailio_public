package com.example.demo.controllers;

import java.time.LocalDate;

import com.example.demo.components.TimePicker;
import com.example.demo.services.DateTimeService;
import javafx.scene.control.DatePicker;

public class DateTimeController {
    private static final DateTimeController instance = new DateTimeController();

    public static DateTimeController getInstance() {
        if (instance != null) {
            return instance;
        }
        return new DateTimeController();
    }
    
    DateTimeService dateTimeService = DateTimeService.getInstance();

    public LocalDate getStartDate() {
        return dateTimeService.getStartDate();
    }

    public LocalDate getSelectedStartDate() {
        return dateTimeService.getSelectedStartDate();
    }
    public LocalDate getSelectedEndDate() {
        return dateTimeService.getSelectedEndDate();
    }

    public LocalDate getEndDate() {
        return dateTimeService.getEndDate();
    }

    public void setStartDate(DatePicker datePicker, TimePicker timePicker) {
        dateTimeService.setStartDate(datePicker);
    }

    public void setEndDate(DatePicker datePicker, TimePicker timePicker) {
        dateTimeService.setEndDate(datePicker);
    }
}
