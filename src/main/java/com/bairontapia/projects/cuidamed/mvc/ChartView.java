package com.bairontapia.projects.cuidamed.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

public class ChartView {

  @FXML
  private ComboBox<String> chartComboBox;
  @FXML
  private BarChart<String, Long> barChart;
  @FXML
  private PieChart pieChart;

  public void onChartSelection(ActionEvent actionEvent) {
  }
}
