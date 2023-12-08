package com.example.demo.models;

import com.example.demo.models.UserPreferenceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPreferenceModelTest {

    private UserPreferenceModel userPreferenceModel;

    @BeforeEach
    public void setUp() {
        userPreferenceModel = new UserPreferenceModel();
    }

    @Test
    public void testStartDate() {
        LocalDateTime expectedStartDate = LocalDateTime.parse("2023-11-06T10:00:00", DateTimeFormatter.ISO_DATE_TIME);
        userPreferenceModel.setStartDate(expectedStartDate);

        LocalDateTime actualStartDate = userPreferenceModel.getStartDate();

        assertEquals(expectedStartDate, actualStartDate);
    }

    @Test
    public void testEndDate() {
        LocalDateTime expectedEndDate = LocalDateTime.parse("2023-11-07T15:30:00", DateTimeFormatter.ISO_DATE_TIME);
        userPreferenceModel.setEndDate(expectedEndDate);

        LocalDateTime actualEndDate = userPreferenceModel.getEndDate();

        assertEquals(expectedEndDate, actualEndDate);
    }
}
