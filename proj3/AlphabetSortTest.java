import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Part of proj3
 * Created by Ray on 8/4/2017.
 */
public class AlphabetSortTest {

    private static String alphabetical = "abcdefghijklmnopqrstuvwxyz";
    private static String given = "agdbecfhijklmnopqrsty";
    private static String reverseAlpha = "zyxwvutsrqpomnlkjihgfedcba";
    private static String repeats = "gthequickbrownfoxjumpsoverthelazydog";
    private static String notcomplete = "bcdfghjklmnpqrstvwxyz";

    // CASE IS DEPRECATED AND DOESN'T USE RIGHT CODE
    // this case was originally used to test the conductSearch method
    // using the 'test' method written at the bottom of the page
    // found a bug in the 'test' method and the conductSearch method,
    // so the test is now unneeded
    public void testConductSearch() {
        ArrayList<String> words = new ArrayList<>();
        words.add("hello");
        words.add("goodbye");
        words.add("goodday");
        words.add("death");
        test(words, alphabetical, "[death, goodbye, goodday, hello]");
        test(words, given, "[goodday, goodbye, death, hello]");
        test(words, reverseAlpha, "[hello, goodday, goodbye, death]");
        ArrayList<String> singleElement = new ArrayList<>();
        singleElement.add("singleelement");
        test(singleElement, alphabetical, "[singleelement]");

        ArrayList<String> simple = new ArrayList<>();
        simple.add("hey");
        simple.add("man");
        simple.add("bro");
        simple.add("brother");
        simple.add("guy");
        simple.add("bud");
        simple.add("buddy");
        test(simple, alphabetical, "[bro, brother, bud, buddy, guy, hey, man]");

//        ArrayList<String> test2 = new ArrayList<>();
//        test2.add("a");
//        test2.add("f");
//        test2.add("s");
//        test2.add("d");
//        test2.add("z");
//        test2.add("q");
//        test2.add("m");
//        test(test2, reverseAlpha, "");
    }


    // makes sure we're finding the right index given a letter position
    @Test
    public void testGetIndex() {
        assertEquals(3, AlphabetSort.getIndex("hello", 1));
        assertEquals(0, AlphabetSort.getIndex("h", 0));
        assertEquals(5, AlphabetSort.getIndex("hellom", 0));
        assertEquals(0, AlphabetSort.getIndex("hello", 4));
    }

    // testing method used to implement the logic in AlphabetSort.main(). it's purpose
    // was to find a bug in main without always pushing to AG, found that bug so this test
    // is now useless/deprecated
    public void test(ArrayList<String> words, String ordering, String expected) {
        HashMap<Character, Integer> alphabet = new HashMap<>();
        System.out.println("Before: " + words);
        int maxWord = 0;
        char[] characters = ordering.toCharArray();
        for (String word : words) {
            if (word.length() > maxWord) {
                maxWord = word.length();
            }
        }
        for (int i = 0; i < characters.length; i++) {
            alphabet.put(characters[i], i);
        }
        AlphabetSort.conductSearch(words, maxWord, alphabet);
        System.out.println("After: " + words);
        assertEquals(expected, words.toString());
        System.out.println("TEST PASSED");
        System.out.println();
    }
}
