import java.util.*;
import java.util.stream.Collectors;

class DFABuilder {
    public static DFA transform(NFA automata) {
        // Retrieve all possible characters
        Set<Character> characters=new HashSet<>(); // All characters in the regex
        Map<ArrayList<Integer>, Boolean> isComplete=new HashMap<>(); // End condition of the while loop
        Map<ArrayList<Integer>, Map<Character, ArrayList<Integer>>> transitionDFA = new HashMap<>(); // new automata with its transitions
        ArrayList<Integer> l=new ArrayList<>();
        int[][] transitionTable=automata.automata.transitionTable; // reference to the NFA transitionTable
        for(int i=0;i<transitionTable.length;i++) for(int j=0;j<transitionTable[i].length;j++)
            if(transitionTable[i][j]!=-1) characters.add((char)j);
        // Initialisation of the first edge of DFA
        l.add(0);
        l=epsilonClosure(l, automata);
        isComplete.put(l, false);
        while(isComplete.containsValue(false)) {
            l=isComplete.keySet().stream().filter(val -> !isComplete.get(val)).collect(Collectors.toList()).get(0);
            isComplete.put(l, true);
            Map<Character, ArrayList<Integer>> charTable = new HashMap<>();
            // watch for all cases possible
            for (Character c : characters) {
                ArrayList<Integer> moveDFA = (epsilonClosure(moveNFA(l, automata, c), automata));
                if(!moveDFA.isEmpty()) charTable.put(c, moveDFA);
                if (!moveDFA.isEmpty() && !isComplete.containsKey(moveDFA)) isComplete.put(moveDFA, false); // to avoid updating the condition if the edge is already on the table
            }
            transitionDFA.put(l, charTable);
        }
        // is the edge on a final state
        Map<ArrayList<Integer>, Boolean> DFAutomata=new HashMap<>();
        for(ArrayList<Integer> sommet: isComplete.keySet()) {
            DFAutomata.put(sommet, sommet.contains(transitionTable.length-1));
        }
        return new DFA(transitionDFA, DFAutomata);
    }

    public static ArrayList<Integer> moveNFA(ArrayList<Integer> previous, NFA automata, char c) {
        int[][] transitionTable=automata.automata.transitionTable;
        Set<Integer> result=new HashSet<>();
        for(Integer i:previous){
            if(transitionTable[i][(int)c]!=-1) result.add(transitionTable[i][(int)c]);
        }
        return (ArrayList<Integer>) result.stream().collect(Collectors.toList());
    }

    public static ArrayList<Integer> epsilonClosure(ArrayList<Integer> moveNFA, NFA automata) {
        ArrayList<Integer> result=new ArrayList<>();
        for(Integer p: moveNFA){
            result.addAll(automata.automata.epsilonTransitionTable[p]);
            result.addAll(epsilonClosure(result, automata));
        }
        result.addAll(moveNFA);
        Set<Integer> resultSet=new HashSet<>(result);
        return (ArrayList<Integer>) resultSet.stream().collect(Collectors.toList());
    }

}
public class DFA {
    protected Map<ArrayList<Integer>, Map<Character, ArrayList<Integer>>> transitionDFA;
    protected Map<ArrayList<Integer>, Boolean> isFinal;
    public DFA(Map<ArrayList<Integer>, Map<Character,
            ArrayList<Integer>>> transitionDFA, Map<ArrayList<Integer>, Boolean> isFinal) {
        this.transitionDFA=transitionDFA;
        this.isFinal=isFinal;
    }
    String finalState(ArrayList<Integer> l, Character c,Integer edge) {
        if(this.isFinal.get(this.transitionDFA.get(l).get(c))) return "(" + edge + ")";
        return edge+"";
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("DFA Transition list:\n");
        String sep = " -- ";
        for (ArrayList<Integer> l: transitionDFA.keySet()) {
            for (Character c: transitionDFA.get(l).keySet()){
                s.append(l.get(0)).append(sep).append(c).append(sep).append("> ").append(finalState(l, c,transitionDFA.get(l).get(c).get(0))).append('\n');
            }
        }
        return s.toString();
    }
    public Map<ArrayList<Integer>, Boolean> getIsFinal() { return isFinal; }
    public Map<ArrayList<Integer>, Map<Character, ArrayList<Integer>>> getTransitionDFA() { return transitionDFA; }
}
