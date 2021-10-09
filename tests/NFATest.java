import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFATest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    NFA automata = new NFA(tree);
    @Test
    public void NFAbuildTest() {
        System.out.println(automata.getAutomata());
        ArrayList<Integer>[] epsilonTransition = automata.getAutomata().getEpsilonTransitionTable();
        int lepsilon=0;
        for (int i=0; i<epsilonTransition.length; i++) lepsilon+=epsilonTransition[i].size();
        assertEquals(lepsilon, 9);
        assertNotEquals(automata.getAutomata().getTransitionTable()[1][97], -1);
    }
}
