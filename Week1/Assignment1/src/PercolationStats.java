import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int gridSize;
    private int trialNum;
    private double[] percolationResult;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid Argument");
        gridSize = n;
        trialNum = trials;
        percolationResult = new double[trials];

        for (int i = 0; i < trials; i++) {
            percolationResult[i] = pTest();
        }
    }

    private double pTest() {
        Percolation percolation = new Percolation(gridSize);
        int row, col;
        double vacant = 0;

        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, gridSize + 1);
            col = StdRandom.uniform(1, gridSize + 1);


            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                vacant++;

            }

        }

        return vacant / (gridSize * gridSize);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationResult);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationResult);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trialNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trialNum);
    }

    public static void main(String[] args) {
        PercolationStats pOut = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean\t\t\t\t\t= %f\n", pOut.mean());
        StdOut.printf("stddev\t\t\t\t\t= %f\n", pOut.stddev());
        StdOut.printf("95%% confidence interval = %f, %f", pOut.confidenceLo(), pOut.confidenceHi());
    }
}
