import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>EGrep class</h1>
 * <p><strong>Description</strong></p>
 * The class <code>EGrep</code> implements a class that will allow the user to search from a text all lines that contains
 * a word that matches a simple regex. <br/>
 * It will visit each lines of the text, to split it by spaces, punctuation, etc. to obtain all word into an ArrayList.
 * For each word, it will check each letter with the graph until it runs into a final state letter. If it is on a final
 * state then the word matches the regex and the line is printed with the line number.
 * @author Bouibker Oussama
 * @author <a href="mailto:alexandre.em@pm.me">Alexandre Em</a>
 */
public class Egrep {
    public static void main(String[] args){
        System.out.println(args[0]+" "+args[1]);
        ArrayList<String> text=new ArrayList<>();
        try {
            File myObj = new File(args[1]);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                text.add(myReader.nextLine());
            myReader.close();
            String regex = args[0];
            ArrayList<String> searchResult = search(text, regex);
            System.out.println(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Search each line that contains a word that matches the regex
     * @param text
     * @param regex
     * @return A List of all line that contains a matching word
     * @throws Exception
     */
    public static ArrayList<String> search(ArrayList<String> text, String regex) throws Exception {
        // Initialisation of the regex to the automata generation
        ArrayList<String> result = new ArrayList<>();
        RegEx r = new RegEx(regex);
        RegExTree regexTree = r.parse();
        NFA nfa = new NFA(regexTree);
        DFA dfa = DFABuilder.transform(nfa);

        for(String line: text) {
            outerloop:
            for(String word: line.split("\\W+")){
                ArrayList<Integer> currentState = dfa.getInitialState(), nextState;
                for(int i=0; i<word.length(); i++) {
                    nextState = dfa.transitionDFA.get(currentState).get(word.charAt(i));
                    if(nextState==null) {
                        // the word didn't match the regex so reset state's pointer of the automata to the initial state
                        if(dfa.isFinal.get(currentState)) { // if currentState is final so the word matches the regex
                            if(result.contains(line)) break outerloop;
                            result.add("\n \u001B[32m[LINE #"+text.indexOf(line)+"] \u001B[0m"+line);
                            i--; // test the current letter in the next iteration
                        }
                        currentState = dfa.getInitialState();
                    } else currentState = nextState;
                }
                if(dfa.isFinal.get(currentState)) // if the last letter matches the regex and is on a final state, so we add the line
                    result.add("\n \u001B[32m[LINE #"+text.indexOf(line)+"] \u001B[0m"+line);
            }
        }
        return result;
    }
}
