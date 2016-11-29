import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private boolean solvable;
    private int minMoves = 0;

    private Stack<Board> solutionBoard = new Stack<>();

    private class SearchNode {
        private final int moves;
        private final Board cur;
        private final SearchNode prev;

        public SearchNode(Board cur, SearchNode prev, int curMove) {
            this.moves = curMove;
            this.prev = prev;
            this.cur = cur;
        }

        public Board getCur() {
            return cur;
        }

        public SearchNode getPrev() {
            return prev;
        }

        public int getMoves() {
            return moves;
        }
    }

    private class BoardCompare implements Comparator<SearchNode> {
        public int compare(SearchNode b1, SearchNode b2) {
            int b1V = b1.cur.hamming() + b1.getMoves();
            int b2V = b2.cur.hamming() + b2.getMoves();

            if (b1V > b2V) return 1;
            else return -1;

        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("NULL BOARD");

        BoardCompare boardCompare = new BoardCompare();

        MinPQ<SearchNode> game = new MinPQ<>(boardCompare);
        MinPQ<SearchNode> twinGame = new MinPQ<>(boardCompare);

        SearchNode curNode = new SearchNode(initial, null, minMoves);
        game.insert(curNode);
        SearchNode twinNode = new SearchNode(initial.twin(), null, minMoves);
        twinGame.insert(twinNode);
        int twinMin = 0;
        this.solvable = true;

        Board curBoard = initial;
        Board twinBoard;

        while (!curBoard.isGoal()) {
            StdOut.println(curBoard.manhattan());
            curNode = game.delMin();
            curBoard = curNode.getCur();
            twinNode = twinGame.delMin();
            twinBoard = twinNode.getCur();

            if (twinBoard.isGoal()) {
                minMoves = -1;
                solvable = false;
                return;
            }

            if (curNode.getPrev() != null) minMoves = curNode.getMoves() + 1;
            if (twinNode.getPrev() != null) twinMin = twinNode.getMoves() + 1;

            for (Board board : curBoard.neighbors()) {
                if (board.equals(curNode.getPrev())) continue;

                game.insert(new SearchNode(board, curNode, minMoves));
            }

            for (Board tBoard : twinBoard.neighbors()) {
                if (tBoard.equals(twinNode.getPrev())) continue;

                twinGame.insert(new SearchNode(tBoard, twinNode, twinMin));
            }
        }

        if (solvable) {
            while (curNode.getPrev() != null) {
                solutionBoard.push(curNode.getCur());
                curNode = curNode.getPrev();
            }

            solutionBoard.push(curNode.getCur());
        } else {
            solutionBoard = null;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionBoard;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            blocks[i] = new int[n];
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
