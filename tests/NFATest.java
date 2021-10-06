import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        System.out.println(automata.getAutomata().toString());
        ArrayList<Integer> l = new ArrayList<>();
        l.add(0);
        l.add(1);
        l.add(3);
        NFA.transform(automata);
//        System.out.println(NFA.moveNFA(l, automata, 'b'));
//        System.out.println(NFA.epsilonClosure(l, automata));
    }
}
