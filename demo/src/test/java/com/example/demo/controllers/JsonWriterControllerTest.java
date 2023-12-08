package com.example.demo.controllers;

import com.example.demo.models.Location;
import com.example.demo.models.UserPreferenceModel;
import com.example.demo.services.JsonReaderService;
import com.example.demo.services.JsonWriterService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterControllerTest {

    private final JsonWriterController jsonWriterController = new JsonWriterController();
    private final JsonReaderService jsonReaderService = new JsonReaderService();
    private final JsonWriterService jsonWriterService = new JsonWriterService();

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

        UserPreferenceModel readFromJson = jsonReaderService.readJsonData();
        assertEquals(readFromJson.getStartDate(), userPreferenceModel.getStartDate());
        assertEquals(readFromJson.getEndDate(), userPreferenceModel.getEndDate());
        assertEquals(readFromJson.getLocation(), userPreferenceModel.getLocation());
        assertEquals(readFromJson.isShowTempData(), userPreferenceModel.isShowTempData());
        assertEquals(readFromJson.isShowTrafficCamera(), userPreferenceModel.isShowTrafficCamera());
        assertEquals(readFromJson.isShowTrafficData(), userPreferenceModel.isShowTrafficData());

    }
}
