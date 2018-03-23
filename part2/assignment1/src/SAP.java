import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph digraph;

    private int graphV() {
        return this.digraph.V();
    }

    private void debug(Object o) {
        StdOut.println(o);
    }

    private int findLength(BreadthFirstDirectedPaths bfsv, BreadthFirstDirectedPaths bfsw, int v, int w) {
        boolean hasPath = false;
        int shortest = -1;
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(v);

        while (!hasPath || !q.isEmpty()) {
            int s = q.dequeue();
            for (int vAdj : this.digraph.adj(s)) {
                q.enqueue(vAdj);
                if (bfsv.hasPathTo(vAdj) && bfsw.hasPathTo(vAdj)) {
                    hasPath = true;

                    final int dist = bfsv.distTo(v) + bfsw.distTo(w);
                    if (dist < shortest || shortest < 0) {
                        shortest = dist;
                    }
                }
            }
        }

        return shortest;
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("INVALID DIGRAPH");

        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v > graphV() || v < 0 || w > graphV() || w < 0) throw new IllegalArgumentException("INVALID VERTEX");

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findLength(bfsv, bfsw, v, w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v > graphV() || v < 0 || w > graphV() || w < 0) throw new IllegalArgumentException("INVALID VERTEX");

        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("INVALID VERTEX");

        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("INVALID VERTEX");

        return 0;
    }

    // private void debugIter(Iterable<T> iterable) {
    //     for (T i : iterable) {
    //         StdOut.println(i);
    //     }
    // }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        // while (!StdIn.isEmpty()) {
        //     int v = StdIn.readInt();
        //     int w = StdIn.readInt();
        //     int length   = sap.length(v, w);
        //     int ancestor = sap.ancestor(v, w);
        //     StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // }
        // BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, 1);
    }
 }
