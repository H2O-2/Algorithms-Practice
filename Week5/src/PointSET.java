import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {
    private SET<Node> bst;

    private static class Node implements Comparable<Node> {
        private Point2D p;

        public Node(Point2D p) {
            this.p = p;
        }

        public Point2D getP() {
            return p;
        }

        public int compareTo(Node that) {
            return p.compareTo(that.getP());
        }
    }

    // construct an empty set of points
    public PointSET() {
        bst = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points in the set
    public int size() {
        return bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("NULL POINT");

        bst.add(new Node(p));
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("NULL POINT");

        return bst.contains(new Node(p));
    }

    // draw all points to standard draw
    public void draw() {
        for (Iterator i = bst.iterator();i.hasNext();) {
            Node node = (Node)i.next();
            node.getP().draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("INVALID RECTANGLE");

        SET<Point2D> rectSet = new SET<>();

        for (Iterator i = bst.iterator();i.hasNext();) {
            Node node = (Node)i.next();
            if (rect.contains(node.getP())) {
                rectSet.add(node.getP());
            }
        }

        return rectSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D minP = null;

        for (Iterator i = bst.iterator();i.hasNext();) {
            Node node = (Node)i.next();
            if (minP == null || node.getP().distanceSquaredTo(p) < minP.distanceSquaredTo(p)) minP = node.getP();
        }

        return minP;
    }

    public static void main(String[] args) {

    }
}
