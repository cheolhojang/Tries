import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
/**
 * AlphabetSort takes input from stdin and prints to stdout.
 * The first line of input is the alphabet permutation.
 * The the remaining lines are the words to be sorted.
 * 
 * The output should be the sorted words, each on its own line, 
 * printed to std out.
 */
public class AlphabetSort {

    /**
     * Reads input from standard input and prints out the input words in
     * alphabetical order.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE. */
        int longWord = 0;
        HashMap<Character, Integer> alphabet = new HashMap<>();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String alphaStr;
        try {
            alphaStr = in.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        HashSet<Character> chars = new HashSet<>();
        for (char c : alphaStr.toCharArray()) {
            chars.add(c);
        }
        if (chars.size() != alphaStr.length()) { //in.isEmpty()) {
            throw new IllegalArgumentException();
        }

        char[] characters = alphaStr.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            alphabet.put(characters[i],  i + 1);
        }

        ArrayList<String> words = new ArrayList<>();
        String newWord;
        try {
            while ((newWord = in.readLine()) != null) {
                boolean addMe = true;
                for (char c : newWord.toCharArray()) {
                    if (!alphabet.containsKey(c)) {
                        addMe = false;
                        break;
                    }
                }
                if (addMe) {
                    words.add(newWord);
                }
                if (newWord.length() > longWord) {
                    longWord = newWord.length();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        if (alphaStr.length() == 0 || words.size() == 0) {
            throw new IllegalArgumentException();
        }
        char nullChar = 0;
        ArrayList<String> newWords = new ArrayList<>();
        for (String word : words) {
            while (word.length() < longWord) {   // buffer the end of every word with null chars
                word += nullChar;
            }
            newWords.add(word);
        }
        alphabet.put(nullChar, 0);
        conductSearch(newWords, longWord, alphabet);

        for (String word : newWords) {
            for (char c : word.toCharArray()) {                 // don't print out null chars
                if (c == 0) {
                    break;
                }
                System.out.print(c);
            }
            System.out.println();
        }

    }
    private static HashMap<Character, Integer> ordering;        // our alphabet
    private static int longestWord;                             // M
    private static int alphabetSize;           // useful to avoid checking ordering.size()

    // implementation of radix sort on list of words given a reference
    // alphabet and the length of the longest word
    // theoretically runs in O(MN) where M is length of longest word
    public static void conductSearch(ArrayList<String> words,
                                     int longWordLength, HashMap<Character, Integer> alphabet) {
        ordering = alphabet;
        longestWord = longWordLength;
        alphabetSize = alphabet.size();
        for (int i = longestWord - 1; i >= 0; i--) {
            sortByCharacter(words, i);
        }
    }

    // helper method for radix sort
    // sorts words given a position (rightmost is 0)
    // uses queues
    public static void sortByCharacter(ArrayList<String> words, int position) {
        Deque[] queues = new Deque[alphabetSize];
        for (int i = 0; i < alphabetSize; i++) {
            queues[i] = new ArrayDeque();
        }
        for (String word : words) {
            int letterIndex = getIndex(word, position);
            char letter = word.charAt(letterIndex);
            if (letter == 0 && word.charAt(letterIndex - 1) != 0) {
                queues[0].addLast(word);
            } else {
                queues[ordering.get(letter)].addLast(word);
            }
        }
        words.clear();
        for (Deque deque : queues) {
            while (!deque.isEmpty()) {
                words.add((String) deque.pop());
            }
        }
    }

    // turns position (far right is 0) into index of a word
    public static int getIndex(String word, int letterNumber) {
        return letterNumber; //word.length() - letterNumber - 1;
    }
}
