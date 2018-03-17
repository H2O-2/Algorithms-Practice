import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] gridState;
    private int gridSize;
    private WeightedQuickUnionUF uf;
    private int gridTop;
    private int gridBottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid Size");

        gridSize = n;

        // initialize grid
        uf = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        gridState = new boolean[gridSize * gridSize + 2];

        gridTop = 0;
        gridBottom = gridSize * gridSize + 1;
    }

    // convert 2D coordinate to 1D
    private int xyConversion(int row, int col) {
        return gridSize * (row - 1) + col;
    }

    // check if row is top row
    private boolean isTop(int row) {
        return row == 1;
    }

    // check if row is bottom row
    private boolean isBottom(int row) {
        return row == gridSize;
    }

    // check if (row, col) is a valid site
    private boolean isValid(int row, int col) {
        return (row > 0 && row <= gridSize) && (col > 0 && col <= gridSize);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row, col)) throw new IndexOutOfBoundsException("Invalid Coordinate");

        gridState[xyConversion(row, col)] = true;
        gridState[0] = true;
        gridState[gridSize * gridSize + 1] = true;

        // connect if site is in top or bottom row
        if (isTop(row)) {

            uf.union(gridTop, xyConversion(row, col));
        } else if (isBottom(row)) {

            uf.union(gridBottom, xyConversion(row, col));
        }

        // up
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            uf.union(xyConversion(row - 1, col), xyConversion(row, col));
        }

        // down
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            uf.union(xyConversion(row + 1, col), xyConversion(row, col));
        }

        // left
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            uf.union(xyConversion(row, col - 1), xyConversion(row, col));
        }

        // right
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            uf.union(xyConversion(row, col + 1), xyConversion(row, col));
        }
    }

    // check if site (row, col) open
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) throw new IndexOutOfBoundsException("Invalid Coordinate");

        return gridState[xyConversion(row, col)];
    }

    // check if site (row, col) full
    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) throw new IndexOutOfBoundsException("Invalid Coordinate");

        return uf.connected(gridTop, xyConversion(row, col));
    }

    // check if the system percolate
    public boolean percolates() {
        return uf.connected(gridTop, gridBottom);
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(10);
        boolean out = test.isFull(1, 1);
        StdOut.println(out);
    }
}
