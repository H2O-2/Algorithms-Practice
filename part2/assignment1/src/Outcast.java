import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException("INVALID WORDNET");

        this.wordNet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException("INVALID NOUNS");

        // List of all distances between nouns:
        // [ [a->b, a->c, a->d...], [b->c, b->d, b->e], [c->d, c->e] ]
        List<List<Integer>> distances = new ArrayList<>();
        int maxDistance = -1;
        String outcast = "";

        for (int i = 0; i < nouns.length; i++) {
            String nounA = nouns[i];
            int dist = 0;
            int n = i;
            for (int m = 0; m < distances.size(); m++) {
                dist += distances.get(m).get(n-1);
                n--;
            }

            List<Integer> newList = new ArrayList<>();
            distances.add(newList);

            for (int j = i + 1; j < nouns.length; j++) {
                String nounB = nouns[j];

                int distanceAB = wordNet.distance(nounA, nounB);
                dist += distanceAB;
                distances.get(i).add(distanceAB);
            }

            if (dist > maxDistance || maxDistance < 0) {
                maxDistance = dist;
                outcast = nouns[i];
            }
        }


        return outcast;
    }

    // private void debug(Object obj) {
    //     StdOut.println(obj);
    // }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
