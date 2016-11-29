import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int n;
    private int zeroX = 0;
    private int zeroY = 0;
    private int[][] boardArray;
    private Queue<Board> neighborBoard = new Queue<>();

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        boardArray = new int[n][n];
        for (int b = 0; b < n; b++) {
            for (int c = 0; c < n; c++) {
                if (blocks[b][c] == 0) {
                    zeroX = c;
                    zeroY = b;
                }

                boardArray[b][c] = blocks[b][c];
            }
        }
    }

    // swap (oldX, oldY) in boardArray with elements on (newX, newY)
    private void swap(int newX, int newY, int oldX, int oldY, int[][] a) {
        int temp = a[newY][newX];
        a[newY][newX] = a[oldY][oldX];
        a[oldY][oldX] = temp;
    }

    private int goalNum(int x, int y) {
        return n * y + x + 1;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int hammingValue = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardArray[i][j] != 0 && boardArray[i][j] != goalNum(j, i)) {
                    hammingValue++;
                }
            }
        }

        return hammingValue;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanValue = 0;

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                int v = boardArray[y][x];
                int goalY = (int) Math.abs(Math.floor(v/n));
                if (v % n == 0) goalY--;
                int goalX = v - n * goalY - 1;
                manhattanValue += Math.abs(y - goalY) + Math.abs(x - goalX);
            }
        }

        return manhattanValue;
    }

    // is this board the goal board?
    public boolean isGoal() {

        boolean goal = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == n - 1 && j==n - 1) continue;

                if (boardArray[i][j] != goalNum(j, i)) {
                    int test1 = boardArray[i][j];
                    int test2 = goalNum(j, i);
                    goal = false;
                    break;
                }
            }
            if (!goal) break;
        }

        return goal;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int twinX = 0;
        int twinY = 1;
        int swapX = 0;
        int swapY = 0;

        if (boardArray[twinY][twinX] == 0) {
            twinX++;
        }

        if (boardArray[swapY][swapX] == 0) {
            swapX++;
        }

        int[][] twinArray = new int[n][n];
        for (int a = 0; a < n; a++) {
            System.arraycopy(boardArray[a], 0, twinArray[a], 0, n);
        }
        swap(twinX, twinY, swapX, swapY, twinArray);

        return new Board(twinArray);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board testEqual = (Board) y;

        return (zeroX == testEqual.zeroX && zeroY == testEqual.zeroY &&
                Arrays.deepEquals(boardArray, testEqual.boardArray));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        if (zeroX - 1 >= 0) {
            int[][] leftN = new int[n][n];
            for (int a = 0; a < n; a++) {
                System.arraycopy(boardArray[a], 0, leftN[a], 0, n);
            }
            this.swap(zeroX - 1, zeroY, zeroX, zeroY, leftN);
            neighborBoard.enqueue(new Board(leftN));
        }

        if (zeroX + 1 < n) {
            int[][] rightN = new int[n][n];
            for (int a = 0; a < n; a++) {
                System.arraycopy(boardArray[a], 0, rightN[a], 0, n);
            }
            this.swap(zeroX + 1, zeroY, zeroX, zeroY, rightN);
            neighborBoard.enqueue(new Board(rightN));
        }

        if (zeroY - 1 >= 0) {
            int[][] upN = new int[n][n];
            for (int a = 0; a < n; a++) {
                System.arraycopy(boardArray[a], 0, upN[a], 0, n);
            }
            this.swap(zeroX, zeroY - 1, zeroX, zeroY, upN);
            neighborBoard.enqueue(new Board(upN));
        }

        if (zeroY + 1 < n) {
            int[][] downN = new int[n][n];
            for (int a = 0; a < n; a++) {
                System.arraycopy(boardArray[a], 0, downN[a], 0, n);
            }
            this.swap(zeroX, zeroY + 1, zeroX, zeroY, downN);
            neighborBoard.enqueue(new Board(downN));
        }

        return neighborBoard;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", boardArray[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());
        initial.isGoal();
    }
}
