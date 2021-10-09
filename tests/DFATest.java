import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DFATest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    NFA nonDeterministicFiniteAutomata = new NFA(tree);
    DFA deterministicFiniteAutomata = DFABuilder.transform(nonDeterministicFiniteAutomata);
    @Test
    public void epsilonClosureTest() {
        ArrayList<Integer> edge = new ArrayList<>(), expected = new ArrayList<>();
        edge.add(4);
        edge.add(5);
        expected.addAll(edge);
        expected.add(6);
        expected.add(8);
        expected.add(9);
        assertEquals(DFABuilder.epsilonClosure(edge, nonDeterministicFiniteAutomata), expected);

        edge = new ArrayList<>();
        expected = new ArrayList<>();
        edge.add(0);
        expected.add(0);
        expected.add(1);
        expected.add(3);
        assertEquals(DFABuilder.epsilonClosure(edge, nonDeterministicFiniteAutomata), expected);
    }
    @Test
    public void moveNFATest() {
        ArrayList<Integer> edge = new ArrayList<>(), expected = new ArrayList<>();
        edge.add(0);
        edge.add(1);
        edge.add(3);
        assertTrue(DFABuilder.moveNFA(edge, nonDeterministicFiniteAutomata, 'a').contains(2));
        assertTrue(DFABuilder.moveNFA(edge, nonDeterministicFiniteAutomata, 'b').contains(4));
        edge= new ArrayList<>();
        edge.add(4);
        edge.add(5);
        edge.add(6);
        edge.add(8);
        edge.add(9);
        assertTrue(DFABuilder.moveNFA(edge, nonDeterministicFiniteAutomata, 'c').contains(7));
    }
    @Test
    public void DFABuildTest(){
        System.out.println(nonDeterministicFiniteAutomata);
        System.out.println(deterministicFiniteAutomata);
        assertEquals(deterministicFiniteAutomata.isFinal.values().stream().filter(val -> val).count(), 3);
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(2);
        edge.add(9);
        assertEquals(deterministicFiniteAutomata.getIsFinal().get(edge), true);
        assertEquals(deterministicFiniteAutomata.getTransitionDFA().get(edge).size(), 0);
        ArrayList<Integer> edge2 = new ArrayList<>();
        edge2.add(0);
        edge2.add(1);
        edge2.add(3);
        assertEquals(deterministicFiniteAutomata.getIsFinal().get(edge2), false);
        assertEquals(deterministicFiniteAutomata.getTransitionDFA().get(edge2).size(), 2);
    }
}
