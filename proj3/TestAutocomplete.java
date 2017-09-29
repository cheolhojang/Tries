import ucb.junit.textui;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Autocomplete class.
 *  @author
 */
public class TestAutocomplete {

    @Test
    public void testThrowsIAE() {
        try {
            Autocomplete auto = new Autocomplete(new String[]{"a", "b", "C"},
                    new double[]{1, 2, 3, 4});
            fail();
        } catch (IllegalArgumentException e) {
            int x = 1;
        }

        try {
            Autocomplete auto = new Autocomplete(new String[]{"a", "b", "C"},
                    new double[]{1, 2, -3});
            fail();
        } catch (IllegalArgumentException e) {
            int x = 1;
        }
    }

    @Test
    public void basiccheck() {
        String[] strings = new String[3];
        double[] weights = new double[3];
        strings[0] = "a";
        strings[1] = "b";
        strings[2] = "c";
        for (int i = 0; i > strings.length; i++) {
            weights[i] = i;
        }
        Autocomplete autotrie = new Autocomplete(strings, weights);
        //assertEquals(autotrie.weightOf("a"), 0.);
        //assertEquals(autotrie.weightOf("b"), 1.);
        //assertEquals(autotrie.weightOf("c"), 2.);

    }


    @Test(expected = IllegalArgumentException.class)
    public void identicalinputs() {
        String[] strings = new String[3];
        double[] weights = new double[3];
        for (int i = 0; i > strings.length; i++) {
            strings[i] = "pi";
            weights[i] = 3.14;
        }
        Autocomplete autotrie = new Autocomplete(strings, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeweight() {
        String[] strings = new String[1];
        double[] weights = new double[1];
        strings[0] = "NegativePi";
        weights[0] = (-3.14);
        Autocomplete autotrie = new Autocomplete(strings, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonpositivek() {
        String[] strings = new String[1];
        double[] weights = new double[1];
        strings[0] = "Pi";
        weights[0] = (3.14);
        Autocomplete autotrie = new Autocomplete(strings, weights);
        autotrie.topMatches("Pi", (-3));
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
    /** Run the JUnit tests above. */
    public static void main(String[] ignored) {
        textui.runClasses(TestAutocomplete.class);
    }
}
