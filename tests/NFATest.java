import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NFATest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    NFA automata = new NFA(tree);
    @Test
    void NFAbuildTest() {
        ArrayList<Integer>[] epsilonTransition = automata.getAutomata().getEpsilonTransitionTable();
        int lepsilon=0;
        for (int i=0; i<epsilonTransition.length; i++) lepsilon+=epsilonTransition[i].size();
        assertEquals(lepsilon, 9);
        assertNotEquals(automata.getAutomata().getTransitionTable()[1][97], -1);
    }
}
