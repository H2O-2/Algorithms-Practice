import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class runClass {
    public static void main(String[] args) {
        MergeBU<Integer> mergeTest = new MergeBU<>();

        int testSize = 99;
        Integer test[] = new Integer[testSize];

        for (int i = 0; i < testSize; i++) {
            test[i] = StdRandom.uniform(0, 1000);
            //StdOut.println(test[i]);
        }

        mergeTest.sort(test);

        for (int j = 0; j < testSize; j++) {
            StdOut.println(test[j]);
        }
    }
}
