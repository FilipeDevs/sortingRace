package Model;

public class SortInfo {

    private String name;
    private int taille;
    private int operations;
    private long duree;

    public SortInfo(String name, int taille, int operations, long duree) {
        this.name = name;
        this.taille = taille;
        this.operations = operations;
        this.duree = duree;
    }

    public String getName() {
        return name;
    }

    public int getTaille() {
        return taille;
    }

    public int getOperations() {
        return operations;
    }

    public long getDuree() {
        return duree;
    }
}
