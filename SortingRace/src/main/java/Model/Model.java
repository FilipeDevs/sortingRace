package Model;

import dp.Observable;
import javafx.application.Platform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model extends Observable {

    private SortAlgorithms sortAlgorithms;
    private String type;
    private ExecutorService threadPool;
    private SortInfo currentSortInfo;
    private int currentThreads;
    private String initDate;
    private String endDate;
    private long totalExecTime;
    private long initExec;
    private double progress;

    public Model() {}

    public void start(String type, int nbrThreads, int maxLength) {
        progress = 0;
        initExec = System.currentTimeMillis();

        this.sortAlgorithms = new SortAlgorithms(maxLength, this);
        this.type = type;
        this.threadPool = Executors.newFixedThreadPool(nbrThreads);
        this.runThreads();
        currentThreads = Thread.activeCount();

    }

    private void calculateExec(long initExec, long endExec) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm::ss z");

        initDate = formatter.format(new Date(initExec));

        endDate = formatter.format(new Date(endExec));

        totalExecTime = endExec - initExec;
    }

    public void setCurrentSortInfo(int length, int nbOp, long exec) {
        currentSortInfo = new SortInfo(type, length, nbOp, exec);
        this.handleProgress();
        this.notifyObservers("sort");
    }

    private void handleProgress() {
        if(progress < 0) {
            progress = 0.1;
        } else {
            progress += 0.1;
            if(progress >= 1.0) { // update de la durrée seulement quand tous les tris ont terminé
                progress = 1.0;
                long endExec = System.currentTimeMillis();
                calculateExec(initExec, endExec);
                this.notifyObservers("endSort");
            }
        }
        this.notifyObservers("progress");
    }

    public SortInfo getCurrentSortInfo() {
        return currentSortInfo;
    }

    private void runThreads() {
        for(int i = 0; i < 11; i++) {
            SortTask task = new SortTask(sortAlgorithms, type);
            threadPool.execute(task);
        }
    }

    public void shutdownThreadPool() {
        threadPool.shutdown();
    }

    public int getCurrentThreads() {
        return currentThreads;
    }

    public String getInitDate() {
        return initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public long getTotalExecTime() {
        return totalExecTime;
    }

    public double getProgress() {
        return progress;
    }
}
