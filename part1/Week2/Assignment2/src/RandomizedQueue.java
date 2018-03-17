import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int head, tail, n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        head = 0;
        tail = 0;
        n = 0;
    }

    private void resize(int l) {
        Item[] newArray = (Item[]) new Object[l];
        int newArrayN = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                newArray[newArrayN] = s[i];
                newArrayN++;
            }
        }
        s = newArray;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("INVALID ITEM");

        if (size() == s.length) {
            resize(s.length * 2);
            head = 0;
            tail = n;
        }

        s[tail] = item;
        tail++;
        n++;

        if (tail == s.length) {
            tail = 0;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException("EMPTY QUEUE");

        Item item = s[head];
        s[head] = null;
        n--;
        if (n > 0) head++;

        if (n > 0 && n == s.length / 4) {
            resize(s.length / 2);
            head = 0;
            tail = n;
        }

        if (head == s.length) head = 0;

        if (s.length == 2 && s[0] == null && s[1] == null) {
            resize(1);
            head = 0;
            tail = 0;
        }

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (n == 0) throw new NoSuchElementException("EMPTY QUEUE");

        Item[] newArray = (Item[]) new Object[n];
        int arrayN = 0;
        int originalLen = s.length;

        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                newArray[arrayN] = s[i];
                arrayN++;
            }
        }

        StdRandom.shuffle(newArray);

        s = newArray;

        resize(originalLen);

        if (n != s.length) {
            head = 0;
            tail = n;
        }

        return s[head];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = head;
        private int arraySize = 0;

        public boolean hasNext() { return arraySize < n; }
        public void remove() { throw new UnsupportedOperationException("NOT IMPLEMENTED"); }
        public Item next() {
            Item returnItem;
            if (i < s.length) {
                if (s[i] == null) throw new NoSuchElementException("END OF ITERATION");
                arraySize++;
                returnItem =  s[i];
                i++;
                return returnItem;
            } else {
                i = 0;
                if (s[i] == null) throw new NoSuchElementException("END OF ITERATION");
                arraySize++;
                returnItem = s[i];
                i++;
                return returnItem;
            }
        }
    }

/*
    public static void main(String[] args) {
        RandomizedQueue rq = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("r")) {
                StdOut.println("pop: " + rq.dequeue());
            } else if (s.equals("s")) {
                StdOut.println("sample: " + rq.sample());
            } else {
                rq.enqueue(s);
            }
            //StdOut.println("n: " + rq.n);

            for (int i = 0; i < rq.s.length; i++) {
                StdOut.println(rq.s[i]);
            }

            /*
            for (Object out:rq){
                StdOut.println(out);
            }
            */
            /*
            StdOut.println("HEAD: " + rq.head);
            StdOut.println(rq.s.length);
            StdOut.println(rq.size());
            StdOut.println(rq.head);
            StdOut.println(rq.s[rq.head]);
            StdOut.println(rq.s[rq.tail - 1]);

        }
    }
*/

}
