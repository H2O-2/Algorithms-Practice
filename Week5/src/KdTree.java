import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class KdTree {
    private Node root;
    private Point2D nearest;

    private static class Node implements Comparable<Node> {
        private Point2D p;
        private RectHV rect;
        private int size;
        private Node lb;
        private Node rt;
        private Node rb;
        private boolean isVertical = true;

        public Node (Point2D p, int size) {
            this.p = p;
            this.size = size;
        }

        public Node(Point2D p, Node lb, Node rt, boolean isVertical) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
            this.isVertical = isVertical;
        }

        public int compareTo(Node that) {
            if (isVertical) {
                if (p.x() > that.p.x()) return 1;
                else return -1;
            } else {
                if (p.y() > that.p.y()) return 1;
                else return -1;
            }
        }
    }



    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    private int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }

    private Node put(Node n, Point2D p) {
        if (n == null) return new Node(p, 1);

        int cmp;

        if (n.isVertical) {
            if (p.x() > n.p.x() || (p.x() == n.p.x() && p.y() > n.p.y())) cmp = 1;
            else cmp = -1;
        } else {
            if (p.y() > n.p.y() || (p.y() == n.p.y() && p.x() > n.p.x())) cmp = 1;
            else cmp = -1;
        }

        if (cmp < 0) {
            n.lb = put(n.lb, p);
            n.lb.rt = n;
            n.lb.isVertical = !n.isVertical;
        } else if (cmp > 0) {
            n.rb = put(n.rb, p);
            n.rb.isVertical = !n.isVertical;
        } else {
            n.p = p;
        }
        n.size = 1 + size(n.lb) + size(n.rb);

        return n;
    }

    private Node get(Node n, Point2D p) {
        if (n == null) return null;

        int cmp = p.compareTo(n.p);

        if (cmp < 0) return get(n.lb, p);
        else if (cmp > 0) return get(n.rb, p);
        else return n;
    }

    private Point2D min() {
        Node min = root;
        while (min.lb != null) min = min.lb;

        return min.p;
    }

    private Point2D max() {
        Node max = root;
        while (max.rb != null) max = max.rb;

        return max.p;
    }

    private void iterable(Node x, Queue<Node> queue) {
        if (x == null) return;

        queue.enqueue(x);
        iterable(x.lb, queue);
        iterable(x.rb, queue);
    }

    private void range(Node n, SET<Point2D> rectSet, RectHV rect) {
        if (n == null) return;

        if (n.isVertical) {
            if (n.p.x() >= rect.xmin() && n.p.x() <= rect.xmax()) {
                range(n.lb, rectSet, rect);
                range(n.rb, rectSet, rect);
            } else if (n.p.x() >= rect.xmax()) {
                range(n.lb, rectSet, rect);
            } else if (n.p.x() <= rect.xmin()){
                range(n.rb, rectSet, rect);
            }
        } else {
            if (n.p.y() >= rect.ymin() && n.p.y() <= rect.ymax()) {
                range(n.lb, rectSet, rect);
                range(n.rb, rectSet, rect);
            } else if (n.p.y() >= rect.ymax()) {
                range(n.lb, rectSet, rect);
            } else if (n.p.y() <= rect.ymin()){
                range(n.rb, rectSet, rect);
            }
        }

        if (rect.contains(n.p)) rectSet.add(n.p);
    }

    private Point2D nearest(Node n, Point2D p, Point2D minP) {
        if (n == null) return null;

        Point2D newP = minP;

        if (newP == null || n.p.distanceSquaredTo(p) < newP.distanceSquaredTo(p)) {
            double test1 = n.p.distanceSquaredTo(p);
            double test2 = 0;

            if (newP != null) test2 = newP.distanceSquaredTo(p);
            double test3 = test2;

            if (n.p.x() == 0.206107) {
                StdOut.println(n.p.x());
            }
            newP = n.p;
        }

        Point2D oldP = newP;

        if (n.isVertical) {
            if (newP.x() <= p.x()) {
                newP = nearest(n.rb, p, newP);

                if (newP == null) {
                    newP = nearest(n.lb, p, oldP);
                } else if (newP.compareTo(oldP) == 0 || newP.x() > p.x()) {
                    newP = nearest(n.lb, p, newP);
                }
            } else {
                newP = nearest(n.lb, p, newP);

                if (newP == null) {
                    newP = nearest(n.rb, p, oldP);
                } else if (newP.compareTo(oldP) == 0 || newP.x() < p.x()) {
                    newP = nearest(n.rb, p, newP);
                }
            }
        } else {
            if (newP.y() <= p.y()) {
                newP = nearest(n.rb, p, newP);

                if (newP == null) {
                    newP = nearest(n.lb, p, oldP);
                } else if (newP.compareTo(oldP) == 0 || newP.y() > p.y()) {
                    newP = nearest(n.lb, p, newP);
                }
            } else {
                newP = nearest(n.lb, p, newP);

                if (newP == null) {
                    newP = nearest(n.rb, p, oldP);
                } else if (newP.compareTo(oldP) == 0 || newP.y() < p.y()) {
                    newP = nearest(n.rb, p, newP);
                }
            }
        }

        if (newP == null) {
            newP = oldP;
        }


        //StdDraw.setPenColor(StdDraw.GREEN);
        //StdDraw.setPenRadius(0.05);
        //newP.draw();
        return newP;
    }

    public Iterable<Node> iterable() {
        Queue<Node> queue = new Queue<>();
        iterable(root, queue);
        return queue;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return root.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("NULL POINT");

        root = put(root, p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("NULL POINT");

        return get(root, p) == null;
    }

    // draw all points to standard draw
    public void draw() {
        for (Node node: iterable()) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();

        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("INVALID RECTANGLE");

        SET<Point2D> rectSet = new SET();

        range(root, rectSet, rect);

        return rectSet;
    }


/*
//DEBUG
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("INVALID RECTANGLE");

        SET<Point2D> rectSet = new SET<>();

        for (Node node : iterable()) {
            if (rect.contains(node.p)) {
                rectSet.add(node.p);
            }
        }

        return rectSet;
    }
*/
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D minP = null;

        minP = nearest(root, p, minP);

        return minP;
    }

    public static void main(String[] args) {

    }
}
