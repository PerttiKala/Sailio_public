package com.example.demo.services;

import com.example.demo.models.UserPreferenceModel;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateTimeService {
    public JsonReaderService jsonReaderService = new JsonReaderService();
    public LocalDate selectedDate = LocalDate.now().minus(7, ChronoUnit.DAYS);
    public LocalDate selectedEndDate = LocalDate.now().minus(6, ChronoUnit.DAYS);

    private static final DateTimeService instance = new DateTimeService();
    public static DateTimeService getInstance() { return instance; };

    public void setStartDate(DatePicker startDate) {
        this.selectedDate = startDate.getValue();
    }

    public void setEndDate(DatePicker endDate) {
        this.selectedEndDate = endDate.getValue();
    }

    public LocalDate getSelectedStartDate() { return selectedDate; }
    public LocalDate getSelectedEndDate() { return selectedDate; }
    public LocalDate getStartDate() {
        LocalDate minus = LocalDate.now().minus(7, ChronoUnit.DAYS);
        UserPreferenceModel userPreferenceModel = jsonReaderService.readJsonData();
        return userPreferenceModel != null && userPreferenceModel.getStartDate() != null ? userPreferenceModel.getStartDate().toLocalDate() : minus;
    }

    public LocalDate getEndDate(){
        LocalDate now = LocalDate.now();
        UserPreferenceModel userPreferenceModel = jsonReaderService.readJsonData();
        return userPreferenceModel != null && userPreferenceModel.getEndDate() != null ? userPreferenceModel.getEndDate().toLocalDate() : now;
    }
}