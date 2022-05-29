package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.AdministrationAggregation.DocumentChoice;
import com.bairontapia.projects.cuidamed.pojo.CountAggregation;
import com.bairontapia.projects.cuidamed.utils.time.TimeUtils;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class ChartView {

  private static final List<String> CHOICES = List.of("Enfermedades", "Medicamentos",
      "Ingesta diaria", "Hora de ingesta");

  @FXML
  private ComboBox<String> chartComboBox;
  @FXML
  private AnchorPane pane;
  @FXML
  private BarChart<String, Long> barChart;
  @FXML
  private PieChart pieChart;

  public void initialize() {
    chartComboBox.getItems().addAll(CHOICES);
  }

  @FXML
  public void onChartSelection(ActionEvent actionEvent) {
    if (chartComboBox.getSelectionModel().isEmpty()) {
      return;
    }
    barChart.setVisible(false);
    pieChart.setVisible(false);
    updateChartTitle();
    fillChart();
  }

  private void updateChartTitle() {
    var index = chartComboBox.getSelectionModel().getSelectedIndex();
    if (index < 2) {
      barChart.setTitle(chartComboBox.getValue());
      return;
    }
    pieChart.setTitle(chartComboBox.getValue());
  }

  private void fillChart() {
    var frequencyMap = retrieveFrequencyMap();
    var index = chartComboBox.getSelectionModel().getSelectedIndex();
    if (index < 2) {
      var list = frequencyMap.entrySet().stream()
          .map(e -> new XYChart.Data<>(e.getKey(), e.getValue())).toList();
      var series = new XYChart.Series<>(FXCollections.observableList(list));
      barChart.getData().clear();
      barChart.getData().add(series);
      barChart.setVisible(true);
      return;
    }
    var list = frequencyMap.entrySet().stream()
        .map(e -> new PieChart.Data(e.getKey(), e.getValue())).toList();
    pieChart.getData().clear();
    pieChart.getData().addAll(FXCollections.observableList(list));
    pieChart.setVisible(true);
  }

  private Map<String, Long> retrieveFrequencyMap() {
    var index = chartComboBox.getSelectionModel().getSelectedIndex();
    return switch (index) {
      case 0 -> CountAggregation.aggregateByCount(DocumentChoice.DIAGNOSTICS, "disease.name",
          String.class);
      case 1 -> CountAggregation.aggregateByCount(DocumentChoice.PRESCRIPTIONS, "medication.name",
          String.class);
      case 2 -> {
        var mapCount = CountAggregation.aggregateByCount(DocumentChoice.PRESCRIPTIONS, "quantity",
            Integer.class);
        yield mapCount.entrySet().stream().collect(
            Collectors.toMap(String::valueOf, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
      }
      case 3 -> {
        var mapCount = CountAggregation.aggregateByCount(DocumentChoice.ADMINISTRATIONS,
            "estimatedDateTime", Date.class);
        yield mapCount.entrySet().stream().collect(
            Collectors.toMap(
                e -> TimeUtils.toLocalTime(e.getKey()).toString(),
                Entry::getValue, (a, b) -> a, LinkedHashMap::new));
      }
      default -> throw new IllegalStateException("Unexpected value: " + index);
    };
  }

}
