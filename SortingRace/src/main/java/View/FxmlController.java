package View;

import Controller.Controller;
import Model.SortInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class FxmlController {

    @FXML
    TableView<SortInfo> table;
    @FXML
    LineChart<Integer, Integer> chart;
    @FXML
    Spinner<Integer> threadSpinner;
    @FXML
    ChoiceBox<String> sortChoice;
    @FXML
    private ChoiceBox<String> configurationChoice;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button start;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private TableColumn<SortInfo, Integer> nameCol;
    @FXML
    private TableColumn<SortInfo, Integer> sizeCol;
    @FXML
    private TableColumn<SortInfo, Integer> swapCol;
    @FXML
    private TableColumn<SortInfo, Integer> durationCol;

    private XYChart.Series<Integer, Integer> bubbleSort;

    private XYChart.Series<Integer, Integer> mergeSort;

    public void initialSetUp() {
        sortChoice.getItems().addAll(FXCollections.observableArrayList("BubbleSort","MergeSort"));
        sortChoice.getSelectionModel().selectFirst();

        configurationChoice.getItems().addAll(FXCollections.observableArrayList("Super Easy 0 - 10","Very Easy : 0 - 100",
                "Easy : 0 - 1000", "Normal : 0 - 10000", "Hard : 0 - 100000"));
        configurationChoice.getSelectionModel().selectFirst();

        threadSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        threadSpinner.setEditable(false);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("taille"));
        swapCol.setCellValueFactory(new PropertyValueFactory<>("operations"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duree"));

        bubbleSort = new XYChart.Series<Integer, Integer>();
        bubbleSort.setName("BubbleSort");

        mergeSort = new XYChart.Series<Integer, Integer>();
        mergeSort.setName("MergeSort");

        chart.setAnimated(false);
    }

    public void setHandler(Controller controller) {
        start.setOnAction(event -> {
            controller.startSort();
        });
    }


    public void setExecutionTime(String initDate, String endDate, long execTime) {
        Platform.runLater(() -> {
            rightStatus.setText("Dérniére execution | Début : " + initDate + " - Fin : "
                    + endDate + " | Durée : " + execTime + "ms");
        });
    }

    public void setCurrentThreadCount(int threadCount) {
        Platform.runLater(() -> {
            leftStatus.setText("Threads actifs : " + threadCount);
        });

    }

    public void setProgressBar(double value) {
        Platform.runLater(() -> {
            progressBar.setProgress(value);
        });
    }

    public String getSortType() {
        return sortChoice.getSelectionModel().getSelectedItem();
    }

    public int getThreadNum() {
        return threadSpinner.getValue();
    }

    public int getMaxLength() {
        String difficulty = configurationChoice.getSelectionModel().getSelectedItem();
        return Integer.parseInt(difficulty.split("- ")[1]);
    }

    public void clearGraph() {
        if(sortChoice.getSelectionModel().getSelectedItem().equals("BubbleSort")) {
            chart.getData().remove(bubbleSort);
            bubbleSort.getData().clear();
            chart.getData().add(bubbleSort);

        } else {
            chart.getData().remove(mergeSort);
            mergeSort.getData().clear();
            chart.getData().add(mergeSort);
        }
    }

    public void addSortInfo(SortInfo sortInfo) {
        Platform.runLater(() -> {
            table.getItems().add(sortInfo);
            if(sortInfo.getName().equals("BubbleSort")) {
                bubbleSort.getData().add(new XYChart.Data<Integer, Integer>(sortInfo.getTaille(), sortInfo.getOperations()));
            } else {
                mergeSort.getData().add(new XYChart.Data<Integer, Integer>(sortInfo.getTaille(), sortInfo.getOperations()));
            }
        });
    }
}