import edu.princeton.cs.algs4.StdOut;

public class MergeBU<Item> {
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

    public void sort(Item[] a) {
        int N = a.length;
        Item[] aux = (Item[]) new Object[N];
        for (int size = 1; size < N; size *= 2) {
            for (int lo = 0; lo < N - size; lo += 2 * size) {
                merge(a, aux, lo, lo + size - 1, Math.min(lo + 2 * size - 1, N - 1));
            }
        }
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
