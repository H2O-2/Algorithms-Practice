import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    private RandomizedQueue<String> rq;

    public Subset() {
        rq = new RandomizedQueue<>();
    }

    private void init(int outNum) {
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < outNum; i++) {
            StdOut.println(rq.sample());
            rq.dequeue();
        }
    }

    public static void main(String[] args) {
        Subset out = new Subset();
        out.init(Integer.parseInt(args[0]));
    }
}
