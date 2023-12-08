package com.example.demo.data.retriever;

import com.example.demo.models.TrafficHistoryModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrafficHistoryDataFetcher {

    public List<TrafficHistoryModel> fetchData(String tmsPointId, int yearShort, int dayNumber) {

        String endpoint = MessageFormat.format(
                "https://tie.digitraffic.fi/api/tms/v1/history/raw/lamraw_{0}_{1}_{2}.csv", tmsPointId, yearShort, dayNumber
        );

        try {
            return fetchData(endpoint);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Error fetching data from endpoint: " + endpoint + " " +  e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static List<TrafficHistoryModel> fetchData(String endpoint) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String csvData = responseBody.string();
                List<String> lines = csvData.lines().collect(Collectors.toList());

                // Skip the header line
                List<String> dataLines = lines.subList(1, lines.size());

                // Parse CSV data and convert it to a list of TrafficHistoryModel objects using Jackson
                return parseCSVToTrafficHistoryModels(dataLines);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    private static List<TrafficHistoryModel> parseCSVToTrafficHistoryModels(List<String> csvLines) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        List<TrafficHistoryModel> trafficHistoryModelList = csvLines.stream()
                .map(line -> {
                    try {
                        String[] tokens = line.split(";");
                        TrafficHistoryModel trafficHistoryModel = new TrafficHistoryModel();
                        trafficHistoryModel.setTmsPointId(Integer.parseInt(tokens[0]));
                        trafficHistoryModel.setYear(Integer.parseInt(tokens[1]));
                        trafficHistoryModel.setOrdinalDate(Integer.parseInt(tokens[2]));
                        trafficHistoryModel.setHour(Integer.parseInt(tokens[3]));
                        trafficHistoryModel.setMinute(Integer.parseInt(tokens[4]));
                        trafficHistoryModel.setSecond(Integer.parseInt(tokens[5]));
                        trafficHistoryModel.setOneHundredthSecond(Integer.parseInt(tokens[6]));
                        trafficHistoryModel.setLength(Double.parseDouble(tokens[7]));
                        trafficHistoryModel.setLane(Integer.parseInt(tokens[8]));
                        trafficHistoryModel.setDirection(Integer.parseInt(tokens[9]));
                        trafficHistoryModel.setVehicleClass(Integer.parseInt(tokens[10]));
                        trafficHistoryModel.setSpeed(Integer.parseInt(tokens[11]));
                        trafficHistoryModel.setFaulty(Integer.parseInt(tokens[12]));
                        trafficHistoryModel.setTotalTimeTechnical(tokens[13]);
                        trafficHistoryModel.setTimeIntervalTechnical(tokens[14]);
                        trafficHistoryModel.setQueueStartTechnical(tokens[15]);
                        return trafficHistoryModel;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return trafficHistoryModelList;
    }

}
