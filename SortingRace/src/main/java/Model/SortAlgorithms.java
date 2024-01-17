package Model;
import java.util.Random;

public class SortAlgorithms {

    private final int maxlength;
    private int currentLength;
    private final Model model;


    public SortAlgorithms(int maxLength, Model model) {
        this.maxlength = maxLength;
        this.currentLength = 0;
        this.model = model;
    }

    public synchronized int[] generateArray() {
        int[] arrayToSort;
        Random random = new Random();
        arrayToSort = random.ints(currentLength, 1, 100).toArray();
        currentLength += maxlength / 10;
        return arrayToSort;
    }

    public void sort(String type, int[] tab) {
        int nbOp;
        long exec = System.currentTimeMillis();
        if(type.equals("BubbleSort")) {
            nbOp = bubbleSort(tab);

        } else {
            nbOp =  mergeSort(tab, tab.length);
        }
        exec = System.currentTimeMillis() - exec;
        model.setCurrentSortInfo(tab.length, nbOp, exec);
    }

    public int bubbleSort(int[] tab) {
        int nbOp = 0;
        int n = tab.length;
        nbOp++;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                nbOp++;
                if (tab[j] > tab[j + 1]) {
                    int temp = tab[j];
                    tab[j] = tab[j + 1];
                    tab[j + 1] = temp;
                    nbOp += 3;
                }
            }
        }
        return nbOp;
    }

    public int mergeSort(int[] tab, int n) {
        int nbOp = 0;
        nbOp++;
        if (n < 2) {
            return nbOp;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        nbOp += 3;

        for (int i = 0; i < mid; i++) {
            l[i] = tab[i];
            nbOp++;
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = tab[i];
            nbOp++;
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        nbOp += merge(tab, l, r, mid, n - mid, nbOp);

        return nbOp;
    }

    private int merge(int[] a, int[] l, int[] r, int left, int right, int nbOp) {
        int i = 0, j = 0, k = 0;
        nbOp += 3;
        while (i < left && j < right) {
            nbOp ++;
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
            nbOp ++;
        }
        while (i < left) {
            a[k++] = l[i++];
            nbOp ++;
        }
        while (j < right) {
            a[k++] = r[j++];
            nbOp ++;
        }
        return nbOp;
    }

}
