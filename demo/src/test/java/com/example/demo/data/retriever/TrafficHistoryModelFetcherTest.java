package com.example.demo.data.retriever;

import com.example.demo.models.TrafficHistoryModel;
import com.example.demo.services.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class TrafficHistoryModelFetcherTest {
    private final TrafficHistoryDataFetcher dataRetriever = new TrafficHistoryDataFetcher();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void fetchData() throws JsonProcessingException {
        String tmsPointId = "101";
        int yearShort = 17;
        int dayNumber = 32;

        List<TrafficHistoryModel> result = dataRetriever.fetchData(tmsPointId, yearShort, dayNumber);
        TrafficHistoryModel actual = objectMapper.readValue("""
                {
                    "tmsPointId": 101,
                    "year": 17,
                    "ordinalDate": 32,
                    "hour": 0,
                    "minute": 0,
                    "second": 23,
                    "oneHundredthSecond": 99,
                    "length": 4.4,
                    "lane": 5,
                    "direction": 2,
                    "vehicleClass": 1,
                    "speed": 79,
                    "faulty": 0,
                    "totalTimeTechnical": "2399",
                    "timeIntervalTechnical": "184",
                    "queueStartTechnical": "0"
                }""", TrafficHistoryModel.class);

        assertEquals(result.get(0), actual);
        System.out.println(result.size());
    }
}