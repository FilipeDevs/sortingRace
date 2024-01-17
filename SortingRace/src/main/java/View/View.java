package View;

import Controller.Controller;
import Model.*;
import dp.Observable;
import dp.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class View implements Observer {

    private FxmlController mainPane;

    public View(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sort.fxml"));
        Parent root = loader.load();
        mainPane = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Observable observable, Object arg) {
        Model model = (Model) observable;
        if(arg.equals("sort")) {
            SortInfo data = model.getCurrentSortInfo();
            mainPane.addSortInfo(data); // add sortData
            mainPane.setCurrentThreadCount(model.getCurrentThreads());
        } else if(arg.equals("progress")) {
            double data = model.getProgress();
            mainPane.setProgressBar(data);
        } else if(arg.equals("endSort")) {
            mainPane.setExecutionTime(model.getInitDate(), model.getEndDate(), model.getTotalExecTime());
        }
    }

    public void initialize() {
        mainPane.initialSetUp();
    }

    public void setHandler(Controller controller) {
        mainPane.setHandler(controller);
    }

    public String getSortType() {
        return mainPane.getSortType();
    }

    public int getThreadNum() {
        return mainPane.getThreadNum();
    }

    public int getMaxLength() {
        return mainPane.getMaxLength();
    }

    public void clearGraph() {
        mainPane.clearGraph();
    }


}
