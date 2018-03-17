import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class runClass {
    public static void main(String[] args) {
        int row, col;
        for (int i = 0; i < 20; i++) {
            row = StdRandom.uniform(1, 5);
            col = StdRandom.uniform(1, 5);
            //StdOut.print(row + " " + col + "\n");
            StdOut.printf("%d %d\n", row, col);
        }

        int N = StdIn.readInt();
        //QuickFindUF quickFindUf = new QuickFindUF(N);
        //QuickUnionUF quickUnionUF = new QuickUnionUF(N);
        //WeightedUF weightedUF = new WeightedUF(N);
        WeightedCompressUF weightedCompressUF = new WeightedCompressUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (p < N && q < N && p >= 0 && q >= 0) {
                if (!weightedCompressUF.connected(p,q)) {
                    weightedCompressUF.union(p, q);
                    StdOut.print(p + " " + q + '\n');
                } else {
                    StdOut.print("connected\n");
                }
            } else {
                throw new IndexOutOfBoundsException("OUT OF BOUND!");
            }
        }
    }
}
