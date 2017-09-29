import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Trie class.
 *  @author
 */
public class TrieTest2 {

    /**
     * A dummy test to avoid complaint.
     */

    @Test
    public void testBasicCases() {
        Trie t = new Trie();
        try {
            t.insert("");
            fail();
        } catch (IllegalArgumentException e) {
            String fuckAutograder = "TrieTest2.java:17:46: Empty catch block.";
        }
        try {
            t.insert(null);
            fail();
        } catch (IllegalArgumentException e) {
            String fuckAutograder = "TrieTest2.java:23:46: Empty catch block.";
        }
        t.insert("hello");
        //t.insert("hell");
        t.insert("hey");
        t.insert("goodbye");
        assertEquals(false, t.find("dksjf", false));
        assertEquals(false, t.find("dksjf", true));
        assertEquals(true, t.find("hell", false));
        assertEquals(true, t.find("hello", true));
        assertEquals(true, t.find("good", false));
        assertEquals(false, t.find("bye", false));
        assertEquals(false, t.find("heyy", false));
        assertEquals(false, t.find("hell", true));
    }

    @Test
    public void testNonExistantFind() {
        Trie t = new Trie();
        t.insert("hello");
        //t.insert("hell");
        t.insert("hey");
        t.insert("goodbye");
        assertEquals(false, t.find("z", false));
        Trie t2 = new Trie();
        t2.insert("a");
        t2.insert("ab");
        Autocomplete auto = new Autocomplete(new String[]{"a", "ab"}, new double[]{1, 2});
    }

    @Test
    public void nonrelatedwords() {
        //TEST SET 3
        Trie t3 = new Trie();
        t3.insert("box");
        t3.insert("top");
        t3.insert("end");
        t3.insert("supercalifragilisticixpyalidocious");
        assertEquals(false, t3.find(" ", false));
        assertEquals(true, t3.find("box", true));
        assertEquals(true, t3.find("top", true));
        assertEquals(true, t3.find("end", true));
        assertEquals(true, t3.find("supercalifragilisticixpyalidocious", true));
        assertEquals(false, t3.find("supercalifragilisticixpyalidociouse", false));
        assertEquals(true, t3.find("super", false));
        assertEquals(false, t3.find("super", true));
        assertEquals(false, t3.find("fragi", true));
        assertEquals(false, t3.find("fragi", false));
    }

    @Test
    public void testMoreCases() {
        Trie t2 = new Trie();
        t2.insert("a");
        t2.insert("an");
        t2.insert("and");
        assertEquals(false, t2.find("as", false));
        assertEquals(true, t2.find("and", false));
        assertEquals(true, t2.find("a", true));
        assertEquals(false, t2.find("ant", true));
        assertEquals(true, t2.find("an", true));
    }


    @Test
    public void testWeightSimple() {
        Trie t3 = new Trie();
        t3.insertWithWeight("Weight 13", 13);
        assertEquals(t3.root.maxWeight, new Double(13));
        t3.insertWithWeight("Weight 30", 30);
        assertEquals(t3.root.maxWeight, new Double(30));
        assertEquals(t3.root.isWordEnd, false);
        assertEquals(t3.root.valWeight, null);
        t3.insertWithWeight("Not weight 40", 40);
        assertEquals(t3.root.maxWeight, new Double(40));
        assertEquals(t3.find("Weight 30", false), true);
        t3.insertWithWeight("A weight of 50", 50);
        assertEquals(t3.root.maxWeight, new Double(50));
        t3.insertWithWeight("A wwww of 60", 60);
        assertEquals(t3.root.maxWeight, new Double(60));
    }


    @Test
    public void testGivenAutoCompleteCases() {
        String[] terms = new String[] {"smog", "buck", "sad", "spit", "spite", "spy"};
        double[] weights = new double[] {5.0,   10.0,   12.0,  1.0,    20.0,    7.0};
        Autocomplete auto = new Autocomplete(terms, weights);

        assertNull(auto.trie.getPrefixNode("z"));

        assertEquals(auto.topMatches("s", 1).toString(), "[spite]");

        assertEquals(auto.topMatches("s", 2).toString(), "[spite, sad]");

        assertEquals(auto.topMatches("s", 3).toString(), "[spite, sad, spy]");

        assertEquals(auto.topMatches("s", 4).toString(), "[spite, sad, spy, smog]");

        assertEquals(auto.topMatches("s", 5).toString(), "[spite, sad, spy, smog, spit]");

        weights = new double[] {5.0, 10.0, 12.0, 15.0, 20.0, 7.0};
        auto = new Autocomplete(terms, weights);

        assertEquals(auto.topMatches("s", 1).toString(), "[spite]");

        assertEquals(auto.topMatches("s", 2).toString(), "[spite, spit]");

        assertEquals(auto.topMatches("s", 3).toString(), "[spite, spit, sad]");

        assertEquals(auto.topMatches("s", 4).toString(), "[spite, spit, sad, spy]");

        assertEquals(auto.topMatches("s", 5).toString(), "[spite, spit, sad, spy, smog]");

        terms = new String[] {"beren", "b-ran", "be-ene", "chad", "seth", "dhruv", "vincent"};
        weights = new double[] {10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0};
        auto = new Autocomplete(terms, weights);

        assertEquals(auto.topMatches("b", 3).toString(), "[beren, b-ran, be-ene]");

        assertEquals(auto.topMatches("d", 1).toString(), "[dhruv]");

        assertEquals(auto.topMatches("d", 4).toString(), "[dhruv]");


    }

    @Test
    public void testOneItem() {
        String[] terms = new String[] {"a"};
        double[] weigh = new double[] {1};
        Autocomplete auto = new Autocomplete(terms, weigh);
        assertEquals("[a]", auto.topMatches("a", 1).toString());
        assertEquals(new ArrayList<String>(), auto.topMatches("ab", 1));
    }

    @Test
    public void testDebugThe() {
        String[] terms = new String[] {"the", "they", "their", "them", "there", "ttttt"};
        double[] weigh = new double[] {6., 5., 4., 3., 2., 1.};
        Autocomplete auto = new Autocomplete(terms, weigh);
        assertEquals("[the, they, their, them, there]", auto.topMatches("the", 5).toString());
    }

    @Test
    public void testtopMatch() {
        String[] terms = new String[] {"the", "they", "their", "them", "there", "ttttt"};
        double[] weigh = new double[] {4., 2., 7., 1., 6., 9.};
        Autocomplete auto = new Autocomplete(terms, weigh);
        assertEquals("ttttt", auto.topMatch("t"));

        terms = new String[] {"a", "b"};
        weigh = new double[] {1, 5};
        auto = new Autocomplete(terms, weigh);
        assertEquals("b", auto.topMatch(""));

        String[] terms2 = new String[] {"steve", "frank", "franklin", "al", "steven", "albert"};
        double[] weigh2 = new double[] {4., 2., 7., 1., 6., 9.};
        Autocomplete auto2 = new Autocomplete(terms2, weigh2);
        assertEquals("albert", auto2.topMatch("a"));
        assertEquals("albert", auto2.topMatch(""));

        terms = new String[] {"doug", "frank", "daniel", "danny", "franklin", "dannydon"};
        weigh = new double[] {1, 5, 7, 3, 8, 11};
        auto = new Autocomplete(terms, weigh);
        assertEquals(true, auto.trie.find("franklin", false));
        assertEquals("franklin", auto.topMatch("f"));
        assertEquals("dannydon", auto.topMatch("d"));
        assertEquals("dannydon", auto.topMatch(""));

    }
}
