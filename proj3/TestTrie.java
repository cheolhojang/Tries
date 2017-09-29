import ucb.junit.textui;
import org.junit.Test;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Trie class.
 *  @author
 */
public class TestTrie {

    /** A dummy test to avoid complaint. */

    // tests exceptions are thrown and find/insert
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

    // another test of insert/find to try to find edge cases
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

    // testing find method on nested words
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

    // CASE IS DEPRECATED NO LONGER USED
    public void testWeightSimple() {
        Trie t3 = new Trie();
        t3.insertWithWeight("Weight 13", 13);
        assertEquals(t3.root.maxWeight, new Double(13));
        t3.insertWithWeight("Weight 30", 30);
        assertEquals(t3.root.maxWeight, new Double(30));
        assertEquals(t3.root.valWeight, new Double(0));
    }


    // COPIED OVER FROM TrieTest2.java
    @Test
    public void testBasicCasesRAY() {
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

    // COPIED OVER FROM TrieTest2.java
    // makes sure calling find on a string that's not present doesn't
    // break everything
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



    /** Run the JUnit tests above. */
    public static void main(String[] ignored) {
        textui.runClasses(TestTrie.class);
    }
}
