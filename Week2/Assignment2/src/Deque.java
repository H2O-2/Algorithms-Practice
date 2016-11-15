import java.util.Iterator;
import java.util.NoSuchElementException;

// import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node first, last;

    private int n;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("INVALID ITEM");

        Node temp = first;
        first = new Node();
        first.item = item;
        first.next = temp;
        if (last == null) {
            last = first;
        } else {
            temp.prev = first;
        }
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("INVALID ITEM");

        Node temp = last;
        if (last == null) last = new Node();
        else {
            last.next = new Node();
            last = last.next;
        }

        last.item = item;
        if (first == null) {
            first = last;
        }
        else {
            last.prev = temp;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null || last == null) throw new NoSuchElementException("EMPTY DEQUE");

        Node temp = first;
        Item removed = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            temp.prev = null;
            first = first.next;
        }
        temp = null;
        n--;

        return removed;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (first == null || last == null) throw new NoSuchElementException("EMPTY DEQUE");

        Item removed = last.item;

        Node temp = last;
        if (last.prev == null) {
            first = null;
            last = null;
        } else {
            last.next = null;
            last = last.prev;
        }
        temp = null;
        n--;

        return removed;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException("NOT IMPLEMENTED"); }

        public Item next() {
            if (current == null) throw new NoSuchElementException("END OF ITERATION");

            Item item = current.item;
            current = current.next;

            return item;
        }
    }


    /*
    public static void main(String[] args) {
        Deque test = new Deque<String>();
        for (int i = 0; i < 5; i++) {
            test.addFirst(i);
            //StdOut.print(test.first.item + "\n");
        }

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            //StdOut.print(test.first.prev + "\n");
            if (s.equals("rf")) {
                test.removeFirst();
            } else if (s.equals("rl")) {
                test.removeLast();
            } else if (s.equals("af")) {
                test.addFirst(1);
            } else {
                test.addLast(s);
            }

            for(Object out:test) StdOut.println(out);
            //StdOut.println("return: " + test.removeLast());
            //StdOut.println(test.isEmpty());
            //StdOut.print(test.first.item + "\n");
            //StdOut.print(test.last.item);
        }
    }
    */

}
