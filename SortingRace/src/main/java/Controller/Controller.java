package Controller;

import Model.Model;
import View.View;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void addObserver() {
        model.addObserver(view);
    }

    public void initialize() {
        view.initialize();
    }

    public void startSort() {
        view.clearGraph();
        model.start(view.getSortType(), view.getThreadNum(), view.getMaxLength());
        model.shutdownThreadPool();
    }
}
