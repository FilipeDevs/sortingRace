package Model;

public class SortTask implements Runnable {

    private SortAlgorithms sortAlgorithms;
    private String type;

    SortTask(SortAlgorithms sortAlgorithms,  String type) {
        this.sortAlgorithms = sortAlgorithms;
        this.type = type;
    }

    @Override
    public void run() {
        int[] tab = sortAlgorithms.generateArray();
        sortAlgorithms.sort(type, tab);
    }

}
