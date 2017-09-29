import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 *
 * @author
 */
public class Autocomplete {
    /**
     * Initializes required data structures from parallel arrays.
     *
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    String[] terms;
    double[] weights;
    Trie trie = new Trie();

    // runs in O(MN)
    // build a useful autocomplete object
    public Autocomplete(String[] terms, double[] weights) {
        /* YOUR CODE HERE. */
        Set<String> set = new HashSet<String>(Arrays.asList(terms));
        if (set.size() != terms.length) {
            throw new IllegalArgumentException();
        }
        this.terms = terms;
        this.weights = weights;
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < terms.length; i++) {
                Double weight = weights[i];
                if (weight < 0) {
                    throw new IllegalArgumentException();
                }
                trie.insertWithWeight(terms[i], weight);
            }
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     *
     * @param term
     * @return
     */

    //Implemented with help of xiaofei on github
    public double weightOf(String term) {
        /* YOUR CODE HERE. */
        if (!trie.find(term, true)) {
            return 0.0;
        }
        return trie.search(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */

    public String topMatch(String prefix) {
        /* YOUR CODE HERE. */
        if (prefix.equals("")) {
            return (String) ((ArrayList) topMatches("", 1)).get(0);
        }
        if (!trie.find(prefix, false)) {
            return null;
        }
        ArrayList<String> matches = (ArrayList) topMatches(prefix, 1);
        return matches.get(0);
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     *
     * @param prefix
     * @param k
     * @return
     */
    public Iterable<String> topMatches(String prefix, int k) { //pushes all the roots
        Trie.TrieNode indNode = trie.root.left;         // to our nodes queue if prefix is ""
        //HashSet<String> wordSet = new HashSet<>();
        ArrayList<Trie.TrieNode> independents = new ArrayList<>();
        if (prefix.equals("")) {
            while (indNode != null) {
                independents.add(indNode); indNode = indNode.left;
            }
            indNode = trie.root.right;
            while (indNode != null) {
                independents.add(indNode); indNode = indNode.right;
            }
        }
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        if (!prefix.equals("") && !trie.find(prefix, false)) {
            return new ArrayList<>();
        }
        Trie.TrieNode preNode;
        if (prefix.equals("")) {
            preNode = trie.root; //independents.get(0);
        } else {
            preNode = trie.getPrefixNode(prefix);
        }
        ArrayList<String> foundWords = new ArrayList<>();
        PriorityQueue<SearchNode> nodes = new PriorityQueue<>();
        for (Trie.TrieNode indepNode : independents) {
            indepNode.visited = true;
            nodes.add(new SearchNode(indepNode, ""));
        }
        PriorityQueue<SearchNode> words = new PriorityQueue<>(Comparator
                .comparing(SearchNode::getInverseValWeight));
        if (preNode.isWordEnd) {
            words.add(new SearchNode(preNode, prefix));
        }
        Trie.TrieNode afterPrefixNode = preNode.middle;
        if (preNode == null) {
            foundWords.add(prefix);
        } else if (afterPrefixNode != null) {
            if (prefix.equals("")) {
                nodes.add(new SearchNode(afterPrefixNode, prefix + preNode.content));
            } else {
                nodes.add(new SearchNode(afterPrefixNode, prefix));
            }
        }
        while (true) {
            PriorityQueue<SearchNode> copiedWords = new PriorityQueue<>(Comparator
                    .comparing(SearchNode::getInverseValWeight));
            while (!words.isEmpty()) {
                SearchNode word = words.poll();
                if ((word.getValWeight() >= preNode.maxWeight
                        || nodes.isEmpty()) && foundWords.size() < k) {
                    foundWords.add(word.word); //wordSet.add(word.word);

                } else {
                    copiedWords.add(word);
                }
            }
            words = copiedWords;
            if (foundWords.size() == k) {
                return foundWords;
            }
            if (nodes.isEmpty()) {
                break;
            }
            SearchNode node = null; Double desiredWeight = preNode.maxWeight;
            while (true) {
                if (!nodes.isEmpty()) {
                    node = nodes.poll();
                } else {
                    break;
                }
                if (node.getValWeight() != null && !node.visited) {
                    node.visited = true;
                    words.add(new SearchNode(node.trieNode, node.word + node.getContent()));
                }
                if (node.left() != null && !node.left().visited) {
                    node.visited = true;
                    nodes.add(new SearchNode(node.left(), node.word));
                }
                if (node.right() != null && !node.right().visited) {
                    node.visited = true;
                    nodes.add(new SearchNode(node.right(), node.word));
                }
                if (node.middle() != null && !node.middle().visited) {
                    node.visited = true;
                    nodes.add(new SearchNode(node.middle(), node.word + node.getContent()));
                }
                if (node.getValWeight() == desiredWeight) {
                    if (!nodes.isEmpty()) {
                        preNode = nodes.peek().trieNode;
                    }
                    break;
                }
            }
        }
        return foundWords;
    }

    // helps to keep track of where we are, and what the string looks like up until this point
    // wraps a node and a partial word together
    private class SearchNode implements Comparable<SearchNode> {
        String word;
        Trie.TrieNode trieNode;
        boolean visited;
        SearchNode(Trie.TrieNode node, String end) {
            trieNode = node;
            visited = false;
            word = end;
        }
        public void addToWord(char c) {
            word += c;
        }
        public Double getInverseValWeight() {
            return -trieNode.valWeight;
        }
        public Double getValWeight() {
            return trieNode.valWeight;
        }
        public Double getMaxWeight() {
            return trieNode.maxWeight;
        }
        public char getContent() {
            return trieNode.content;
        }
        public Trie.TrieNode left() {
            return trieNode.left;
        }
        public Trie.TrieNode right() {
            return trieNode.right;
        }
        public Trie.TrieNode middle() {
            return trieNode.middle;
        }
        @Override
        public int compareTo(SearchNode other) {
            return other.trieNode.maxWeight.intValue() - trieNode.maxWeight.intValue();
        }
        public String toString() {
            return "SearchNode(" + trieNode + ", " + word + ")";
        }

    }

    /**
     * Test client. Reads the data from the file, then repeatedly reads autocomplete
     * queries from standard input and prints out the top k matching terms.
     *
     * @param args takes the name of an input file and an integer k as
     *             command-line arguments
     */

    public static void main(String[] args) {
        // initialize autocomplete data structure

        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
