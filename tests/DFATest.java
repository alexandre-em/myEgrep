import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DFATest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    NFA nonDeterministicFiniteAutomata = new NFA(tree);
    DFA deterministicFiniteAutomata = DFABuilder.transform(nonDeterministicFiniteAutomata);
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
