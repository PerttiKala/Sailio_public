package com.example.demo.facades.interfaces;

import com.example.demo.dto.LineChartFetchDto;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public interface ILineChartFacade <Model> {
    ArrayList<XYChart.Series<String, Number>> fetchData(Model model);
}
