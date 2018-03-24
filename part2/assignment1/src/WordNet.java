import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class WordNet {

    private List<String> wordNetNouns = new ArrayList<>();
    private List<String> synsetsString = new ArrayList<>();
    private List<List<String>> synsets = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    private Digraph wordNet;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("INVALID INPUT FILE");
        }

        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        String nextSynset, nextHypernym;
        List<List<Integer>> hypernymsList = new ArrayList<>();

        nextSynset = synsetsIn.readLine();
        nextHypernym = hypernymsIn.readLine();

        while (nextSynset != null) {
            List<String> synsetList = Arrays.asList(nextSynset.split(","));
            this.ids.add(Integer.parseInt(synsetList.get(0)));
            this.synsetsString.add(synsetList.get(1));
            final List<String> synset = Arrays.asList(synsetList.get(1).split(" "));
            // add synset
            this.synsets.add(synset);

            // remove duplicates
            Set<String> synsetsStringSet = new LinkedHashSet<>();
            synsetsStringSet.addAll(synset);
            this.wordNetNouns.addAll(synsetsStringSet);

            // init hypernymsList
            List<Integer> emptyList = new ArrayList<>();
            hypernymsList.add(emptyList);

            nextSynset = synsetsIn.readLine();
        }

        while (nextHypernym != null) {
            List<String> hypernymStringList = Arrays.asList(nextHypernym.split(","));
            List<Integer> hypernymList = new ArrayList<>();
            for (String s : hypernymStringList) {
                hypernymList.add(Integer.parseInt(s));
            }
            // get id
            final int curId = hypernymList.get(0);
            // remove id
            hypernymList.remove(0);
            hypernymsList.get(curId).addAll(hypernymList);
            nextHypernym = hypernymsIn.readLine();
        }

        this.wordNet = new Digraph(this.synsets.size());

        // initialize WordNet
        int curId = 0;
        for (List<Integer> hypernymIds : hypernymsList) {
            for (int hypernymId : hypernymIds) {
                this.wordNet.addEdge(curId, hypernymId);
            }
            curId++;
        }


        // get index of entity (root)
        // final int rootIndex = this.synsets.indexOf(Collections.singletonList("entity"));
        // run BFS on the reverse of given DAG to check if it is rooted
        // BreadthFirstDirectedPaths bfs =
        //     new BreadthFirstDirectedPaths(this.wordNet.reverse(), rootIndex);
        // for (int n = 0; n < this.wordNet.V(); n++) {
        //     if (!bfs.hasPathTo(n)) {
        //         throw new IllegalArgumentException("INPUT NOT ROOTED");
        //     }
        // }

        this.sap = new SAP(this.wordNet);
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
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("INVALID NOUN");

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();

        int i = 0;
        for (List<String> synset : this.synsets) {
            final int curId = this.ids.get(i);
            for (String noun : synset) {
                if (noun.equals(nounA)) {
                    listA.add(curId);
                }

                if (noun.equals(nounB)) {
                    listB.add(curId);
                }
            }
            i++;
        }

        return this.sap.length(listA, listB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("INVALID NOUN");

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();

        int i = 0;
        for (List<String> synset : this.synsets) {
            final int curId = this.ids.get(i);
            for (String noun : synset) {
                if (noun.equals(nounA)) {
                    listA.add(curId);
                }

                if (noun.equals(nounB)) {
                    listB.add(curId);
                }
            }
            i++;
        }

        int ancestorIndex = this.sap.ancestor(listA, listB);

        return this.synsetsString.get(ancestorIndex);
    }

    // private void debug(Object obj) {
    //     StdOut.println(obj);
    // }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        StdOut.println(wordNet.distance("a", "c"));

        // Digraph digraph = new Digraph(2);
        // digraph.addEdge(0, 1);
        // StdOut.println(digraph.toString());
    }
}
