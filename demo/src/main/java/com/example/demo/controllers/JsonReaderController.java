package com.example.demo.controllers;

import com.example.demo.models.UserPreferenceModel;
import com.example.demo.services.JsonReaderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReaderController {
    JsonReaderService jsonReaderService = new JsonReaderService();
    public UserPreferenceModel readJsonData() {
        return jsonReaderService.readJsonData();
    }
}
