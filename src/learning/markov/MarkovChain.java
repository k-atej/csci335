package learning.markov;

import learning.core.Histogram;

import java.util.*;

public class MarkovChain<L,S> {
    private LinkedHashMap<L, HashMap<Optional<S>, Histogram<S>>> label2symbol2symbol = new LinkedHashMap<>();

    public Set<L> allLabels() {return label2symbol2symbol.keySet();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (L language: label2symbol2symbol.keySet()) {
            sb.append(language);
            sb.append('\n');
            for (Map.Entry<Optional<S>, Histogram<S>> entry: label2symbol2symbol.get(language).entrySet()) {
                sb.append("    ");
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue().toString());
                sb.append('\n');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Increase the count for the transition from prev to next.
    // Should pass SimpleMarkovTest.testCreateChains().
    public void count(Optional<S> prev, L label, S next) {
        if (label2symbol2symbol.containsKey(label)) {
            if (label2symbol2symbol.get(label).containsKey(prev)) {
                    label2symbol2symbol.get(label).get(prev).bump(next);

            }
            else {
                Histogram<S> hist = new Histogram<>();
                hist.bump(next);
                label2symbol2symbol.get(label).put(prev, hist);
            }
        }
        else {
            Histogram<S> hist = new Histogram<>();
            hist.bump(next);
            HashMap<Optional<S>, Histogram<S>> hash = new HashMap<>();
            hash.put(prev, hist);
            label2symbol2symbol.put(label, hash);
        }
        // TODO: YOUR CODE HERE
    }

    // Returns P(sequence | label)
    // Should pass SimpleMarkovTest.testSourceProbabilities() and MajorMarkovTest.phraseTest()
    //
    // HINT: Be sure to add 1 to both the numerator and denominator when finding the probability of a
    // transition. This helps avoid sending the probability to zero.
    public double probability(ArrayList<S> sequence, L label) {
        ArrayList<Float> arr = new ArrayList<>();
        int i = 0;
        int num;
        int denom;
        boolean running = true;
        while (running) {
            if (i >= sequence.size() - 1) {
                running = false;
            }
            else {
                S s = sequence.get(i);
                if (label2symbol2symbol.get(label).containsKey(Optional.of(s))){
                    num = 1 + label2symbol2symbol.get(label).get(Optional.of(s)).getCountFor(sequence.get(i + 1));
                    denom = 1 + label2symbol2symbol.get(label).get(Optional.of(s)).getTotalCounts();

                    float val = (float) num/denom;
                    arr.add(i, val);
                }
                else {
                    arr.add(i, 1.0F);
                }
            }
            i += 1;
        }
        float total = 1;
        for (float value : arr) {
            total = total * value;
        }

        // TODO: YOUR CODE HERE
        return total;
    }

    // Return a map from each label to P(label | sequence).
    // Should pass MajorMarkovTest.testSentenceDistributions()
    public LinkedHashMap<L,Double> labelDistribution(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> hash = new LinkedHashMap<>();
        LinkedHashMap<L, Double> hash2 = new LinkedHashMap<>();
        Set<L> labels = allLabels();
        double total = 0;
        for (L label : labels) {
            double dub = probability(sequence, label);
            hash.put(label, dub);
            total += dub;
        }
        for (L key : hash.keySet()) {
            hash2.put(key, hash.get(key) / total);
        }
        // TODO: YOUR CODE HERE
        return hash2;
    }

    // Calls labelDistribution(). Returns the label with highest probability.
    // Should pass MajorMarkovTest.bestChainTest()
    public L bestMatchingChain(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> hash = labelDistribution(sequence);
        ArrayList<L> arr = new ArrayList<>();
        Set<L> s = hash.keySet();
        int i = 0;
        for (L label : s) {
            arr.add(i, label);
            i += 1;
        }
        L highest = arr.get(0);
        for (L key : s) {
            if ((hash.get(key)) > hash.get(highest)) {
                highest = key;
            }
        }
        // TODO: YOUR CODE HERE
        return highest;
    }
}
