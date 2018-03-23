import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class WordNet {
    private List<String> wordNetNouns = new ArrayList<>();
    private List<List<String>> synsets = new ArrayList<>();
    private List<List<Integer>> hypernyms = new ArrayList<>();
    private Digraph wordNet;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("INVALID INPUT FILE");
        }

        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        List<Integer> ids = new ArrayList<>();
        String nextSynset, nextHypernym;

        nextSynset = synsetsIn.readLine();
        nextHypernym = hypernymsIn.readLine();

        while (nextSynset != null && nextHypernym != null) {
            List<String> synsetList = Arrays.asList(nextSynset.split(","));
            ids.add(Integer.parseInt(synsetList.get(0)));
            final List<String> synset = Arrays.asList(synsetList.get(1).split(" "));
            // add synset
            this.synsets.add(synset);
            this.wordNetNouns.addAll(synset);

            List<String> hypernymStringList = Arrays.asList(nextHypernym.split(","));
            List<Integer> hypernymList = new ArrayList<>();
            for (String s : hypernymStringList) {
                hypernymList.add(Integer.parseInt(s));
            }
            // remove id
            hypernymList.remove(0);
            this.hypernyms.add(hypernymList);

            nextSynset = synsetsIn.readLine();
            nextHypernym = hypernymsIn.readLine();
        }

        this.wordNet = new Digraph(this.synsets.size());

        // initialize WordNet
        int curId = 0;
        for (List<Integer> hypernymIds : this.hypernyms) {
            for (int hypernymId : hypernymIds) {
                this.wordNet.addEdge(curId, hypernymId);
            }
            curId++;
        }


        // get index of entity (root)
        final int rootIndex = this.synsets.indexOf(Collections.singletonList("entity"));
        // run BFS on the reverse of given DAG to check if it is rooted
        BreadthFirstDirectedPaths bfs =
            new BreadthFirstDirectedPaths(this.wordNet.reverse(), rootIndex);
        for (int n = 0; n < this.wordNet.V(); n++) {
            if (!bfs.hasPathTo(n)) {
                throw new IllegalArgumentException("INPUT NOT ROOTED");
            }
        }

        // debug(this.wordNet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.wordNetNouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("INVALID WORD");

        if (this.wordNetNouns.indexOf(word) < 0) {
            return false;
        }

        return true;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("INVALID NOUN");

        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("INVALID NOUN");

        return "";
    }

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
