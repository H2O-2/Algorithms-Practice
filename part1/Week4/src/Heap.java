import edu.princeton.cs.algs4.StdRandom;

public class Heap {
    private final int[] a;
    private int capacity;

    public Heap(int[] a, int capacity) {
        this.a = a;
        this.capacity = capacity;
    }

    private void sink(int k) {
        while (2 * k <= capacity) {
            int cur = 2*k;

            if (cur < capacity && less(cur, cur+ 1)) cur++;
            if (!less(k, cur)) break;

            exch(k, cur);
            k = cur;
        }
    }

    public void sort() {
        for (int i = capacity/2; i > 0; i--) {
            sink(i);
        }

        while (capacity > 1) {
            exch(capacity, 1);
            capacity--;
            sink(1);
        }
    }

    public boolean less(int m, int n) {
        return a[m - 1] < a[n - 1];
    }

    public void exch(int m, int n) {
        int temp = a[m - 1];
        a[m - 1] = a[n - 1];
        a[n - 1] = temp;
    }

    public static void main(String args[]) {
        int len = 100;
        int[] array = new int[len];

        for (int i = 0; i < len; i++) {
            array[i] = StdRandom.uniform(0,1000);
        }

        Heap test = new Heap(array,len);
        test.sort();
    }
}
