/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 *
 * @author
 */


public class Trie {
    static boolean zdebug = false;

    // for convenience in debugging
    public static void debugPrint(Object... strings) {
        if (!zdebug) {
            return;
        }
        for (Object s : strings) {
            System.out.print(s);
        }
    }


    // Dont' make private because we need to create new ones in
    // Autocomplete class
    class TrieNode {
        boolean isWordEnd;
        boolean visited;
        char content;

        Double maxWeight;
        Double valWeight;

        TrieNode left;
        TrieNode middle;
        TrieNode right;

        public String toString() {
            return "TrieNode(" + content + ", " + isWordEnd + ", Val: "
                    + valWeight + ", " + "Max: " + maxWeight + ")";
        }
        TrieNode(char character, boolean end, Double weight) {
            this.content = character;
            this.visited = false;
            this.isWordEnd = end;
            this.maxWeight = weight;
            if (isWordEnd) {
                this.valWeight = weight;
            }
            this.left = null;
            this.middle = null;
            this.right = null;
        }

    }
    TrieNode root;

    public Trie() {
        root = null;
    }

    // implemented with help from sanfoundry.com
    // adds char string along proper node sequence
    // should work logarithmically in best case
    public TrieNode add(TrieNode node, char[] chars, int index, Double weight) {
        boolean isEnd = (chars.length == index + 1);
        if (index == chars.length) {
            return null;
        }
        if (node == null) {
            node = new TrieNode(chars[index], isEnd, weight);
        }
        if (weight > node.maxWeight) {
            node.maxWeight = weight;
        }
        if (chars[index] < node.content) {
            debugPrint("    add ", node, " left to ", node.left);
            node.left = add(node.left, chars, index, weight);
        } else if (chars[index] > node.content) {
            debugPrint("    add ", node, " right to ", node.right);
            node.right = add(node.right, chars, index, weight);
        } else {
            if (index < chars.length - 1) {
                debugPrint("    add ", node, " down to ", node.middle);
                node.middle = add(node.middle, chars, index + 1, weight);
            } else {
                node.isWordEnd = true;
                node.valWeight = weight;
                debugPrint("    end insert on ", node);
            }
        }
        return node;
    }

    // calls add with given weight
    public void insertWithWeight(String s, double w) {
        root = add(root, s.toCharArray(), 0, w);
    }

    // easy insert method for use
    public void insert(String s) {
        /* YOUR CODE HERE. */
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        debugPrint("insert '", s, "' at ", root);
        root = add(root, s.toCharArray(), 0, 0.0);
    }

    // implemented with help of sanfoundry.com
    // recursively called on trienodes, navegates the trie
    public boolean find(TrieNode node, char[] chars, int index, boolean isFullWord) {
        boolean atCharsEnd = chars.length == index + 1;
        if (node == null) {
            return false;
        }
        if (node.content > chars[index]) {
            return find(node.left, chars, index, isFullWord);
        } else if (node.content < chars[index]) {
            return find(node.right, chars, index, isFullWord);
        } else {
            if (atCharsEnd) {
                if (isFullWord) {
                    return node.isWordEnd;
                }
                return true;
            } else {
                return find(node.middle, chars, index + 1, isFullWord);
            }
        }
    }

    // Nicer find method, for simpler use
    public boolean find(String s, boolean isFullWord) {
        return find(root, s.toCharArray(), 0, isFullWord);
    }

    //implemented with help of xiaofei on github for ternarytrie.java
    public double search(String s) {
        TrieNode searchnode = getPrefixNode(s);
        if (searchnode.equals(null)) {
            return 0.0;
        } else {
            return searchnode.valWeight;
        }
    }

    // searches for node at which prefix ends (logarithmic)
    public TrieNode getPrefixNode(String prefix) {
        if (!find(prefix, false)) {
            return null;
        }
        return getPrefixNode(root, prefix.toCharArray(), 0);
    }

    // intense prefix node finder, don't use in other places :) (logarithmic)
    private static TrieNode getPrefixNode(TrieNode node, char[] chars, int index) {
        if (node == null) {
            return null;
        } else if (node.content > chars[index]) {
            return getPrefixNode(node.left, chars, index);
        } else if (node.content < chars[index]) {
            return getPrefixNode(node.right, chars, index);
        } else {
            if (chars.length == index + 1) {               // out of chars
                return node;
            } else {
                return getPrefixNode(node.middle, chars, index + 1);
            }
        }
    }
}
