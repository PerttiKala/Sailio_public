package com.example.demo.controllers;

import com.example.demo.models.UserPreferenceModel;
import com.example.demo.services.JsonWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class JsonWriterController {
    JsonWriterService jsonWriterService = new JsonWriterService();
    public void writeJsonData(UserPreferenceModel data) {
        jsonWriterService.writeJsonData(data);
    }

}
