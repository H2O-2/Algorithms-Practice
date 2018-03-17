public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key;
        Node left, right;
        boolean color;

        public Node(int key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color;
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert (!isRed(h));
        assert (isRed(h.left));
        assert (isRed(h.right));

        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public Node put(Node h, int key) {
        if (h == null) return new Node(key, RED);

        if (h.key > key) h.left = put(h.left, key);
        else if (h.key < key) h.right = put(h.right, key);
        else h.key = key;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }
}
