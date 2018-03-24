import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph digraph;
    private BreadthFirstDirectedPaths bfsv;
    private BreadthFirstDirectedPaths bfsw;

    private int shortestDist = -1;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("INVALID DIGRAPH");

        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v > graphV() || v < 0 || w > graphV() || w < 0) throw new IllegalArgumentException("INVALID VERTEX");

        ancestor(v, w);

        return this.shortestDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v > graphV() || v < 0 || w > graphV() || w < 0) throw new IllegalArgumentException("INVALID VERTEX");

        this.bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        this.bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findAncestor(bfsv, bfsw);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("INVALID VERTEX");

        ancestor(v, w);

        return this.shortestDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("INVALID VERTEX");

        this.bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        this.bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findAncestor(bfsv, bfsw);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        // BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, 1);
    }

    private int graphV() {
        return this.digraph.V();
    }

    // private void debug(Object obj) {
    //     StdOut.println(obj);
    // }

    // private int findAncestor(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2, int v, int w) {
    private int findAncestor(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int shortest = -1;
        int shortestAncestor = -1;
        Queue<Integer> q = new Queue<Integer>();

        // q.enqueue(v);

        // while (!q.isEmpty()) {
        //     int s = q.dequeue();

        //     if (bfs1.hasPathTo(s) && bfs2.hasPathTo(s)) {

        //         final int dist = bfs1.distTo(s) + bfs2.distTo(s);
        //         if (dist < shortest || shortest < 0) {
        //             shortest = dist;
        //             shortestAncestor = s;
        //         }
        //     }

        //     for (int vAdj : this.digraph.adj(s)) {
        //         q.enqueue(vAdj);
        //         if (bfs1.hasPathTo(vAdj) && bfs2.hasPathTo(vAdj)) {

        //             final int dist = bfs1.distTo(vAdj) + bfs2.distTo(vAdj);
        //             if (dist < shortest || shortest < 0) {
        //                 shortest = dist;
        //                 shortestAncestor = vAdj;
        //             }
        //         }
        //     }
        // }

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {

                final int dist = bfs1.distTo(i) + bfs2.distTo(i);
                if (dist < shortest || shortest < 0) {
                    shortest = dist;
                    shortestAncestor = i;
                }
            }
        }

        this.shortestDist = shortest;

        return shortestAncestor;
    }

    private int findAncestor(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2,
                             Iterable<Integer> vIter, Iterable<Integer> wIter) {
        int shortest = -1;
        int shortestAncestor = -1;
        List<Integer> searchedV = new ArrayList<>();

        for (int v : vIter) {
            Queue<Integer> q = new Queue<Integer>();
            q.enqueue(v);

            while (!q.isEmpty()) {
                int s = q.dequeue();

                if (bfs1.hasPathTo(s) && bfs2.hasPathTo(s)) {

                    final int dist = bfs1.distTo(s) + bfs2.distTo(s);
                    if (dist < shortest || shortest < 0) {
                        shortest = dist;
                        shortestAncestor = s;
                    }
                }

                for (int vAdj : this.digraph.adj(s)) {
                    if (searchedV.contains(vAdj)) continue;

                    q.enqueue(vAdj);
                    searchedV.add(vAdj);
                    if (bfs1.hasPathTo(vAdj) && bfs2.hasPathTo(vAdj)) {

                        final int dist = bfs1.distTo(vAdj) + bfs2.distTo(vAdj);
                        if (dist < shortest || shortest < 0) {
                            shortest = dist;
                            shortestAncestor = vAdj;
                        }
                    }
                }
            }
        }

        this.shortestDist = shortest;

        return shortestAncestor;
    }
}
