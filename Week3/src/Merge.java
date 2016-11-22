import edu.princeton.cs.algs4.StdOut;

public class Merge<Item> {

    private Item aux[];

    private void merge(Item[] a, Item[] aux, int lo, int mid, int hi) {
        assert (isSorted(a, lo, mid));      // precondition: a[lo..mid] sorted
        assert (isSorted(a, mid + 1, hi));  // precondition: a[mid+1..hi] sorted

        for (int n = lo; n <= hi; n++) {
            aux[n] = a[n];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j];
                j++;
            } else if (j > hi) {
                a[k] = aux[i];
                i++;
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j];
                j++;
            } else {
                a[k] = aux[i];
                i++;
            }
        }

        assert (isSorted(a, lo, hi));

    }

    private void sort(Item[] a, Item[] aux, int lo, int hi) {
        if (hi <= lo) return;

        int mid = lo + (hi - lo) / 2;

        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        if (!less(a[mid + 1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    public void sort(Item[] a) {
        aux = (Item[]) new Object[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private boolean isSorted(Item[] a, int lo, int hi) {
        return true;
    }

    private boolean less(Item a, Item b) {
        if (a instanceof String) {
            return ((String) a).compareTo((String) b) < 0;
        }

        return (Integer) a < (Integer) b;
    }
}
