import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private List<String[]> synsets = new ArrayList<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("INVALID INPUT FILE");
        }

        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        List<Integer> ids = new ArrayList<>();
        String nextSynset, nexthypernym;

        nextSynset = synsetsIn.readLine();
        nexthypernym = hypernymsIn.readLine();

        while (nextSynset != null && nexthypernym != null) {
            String[] synsetArr = nextSynset.split(",");
            ids.add(Integer.parseInt(synsetArr[0]));
            this.synsets.add(Arrays.copyOfRange(synsetArr, 1, synsetArr.length - 2));

            nextSynset = synsetsIn.readLine();
            nexthypernym = hypernymsIn.readLine();
        }
    }

    // returns all WordNet nouns
    // public Iterable<String> nouns() {}

    // is the word a WordNet noun?
    // public boolean isNoun(String word) {}

    // distance between nounA and nounB (defined below)
    // public int distance(String nounA, String nounB) {}

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // public String sap(String nounA, String nounB) {}

    private void debug(Object o) {
        StdOut.println(o);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);

        // Digraph digraph = new Digraph(2);
        // digraph.addEdge(0, 1);
        // StdOut.println(digraph.toString());
    }
}
