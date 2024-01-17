package com.example.demo.controllers;

import com.example.demo.models.Location;
import com.example.demo.models.UserPreferenceModel;
import com.example.demo.services.JsonWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

public class JsonWriterControllerUnitTest {

    private JsonWriterController jsonWriterController;
    private JsonWriterService jsonWriterService;

    @BeforeEach
    public void setUp() {
        jsonWriterService = Mockito.mock(JsonWriterService.class);
        jsonWriterController = new JsonWriterController();
        jsonWriterController.jsonWriterService = jsonWriterService;
    }

    @Test
    public void testWriteJsonData() throws IOException {
        // Create a sample UserPreferenceModel
        UserPreferenceModel userPreferenceModel = new UserPreferenceModel();
        userPreferenceModel.setStartDate(LocalDateTime.now());
        userPreferenceModel.setEndDate(LocalDateTime.now().plusHours(1));
        userPreferenceModel.setLocation(new Location("Helsinki", "23172", "C01650", "Helsinki, Landbo, Road 7", "25.209370", "60.266368", "101"));
        userPreferenceModel.setShowTrafficData(true);
        userPreferenceModel.setShowTempData(false);
        userPreferenceModel.setShowTrafficCamera(true);

        // Call the writeJsonData method
        jsonWriterController.writeJsonData(userPreferenceModel);

        // Verify that the writeJsonData method of JsonWriterService is called with the correct argument
        verify(jsonWriterService).writeJsonData(userPreferenceModel);
    }
}
