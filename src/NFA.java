import java.util.ArrayList;

public class NFA {
    static final int N = 256;
    protected RegExTree regExTree;
    protected NDFA automata;
    public NFA(RegExTree regExTree) {
        this.regExTree=regExTree;
        this.automata=this.build(regExTree);
    }
    public NDFA build(RegExTree regExTree) {
        if (regExTree.subTrees.isEmpty()) return emptyCase(regExTree);
        if (regExTree.root==RegEx.ETOILE) return etoileCase(regExTree);

        //IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS transitionTable.length-1
        NDFA gauche = build(regExTree.subTrees.get(0));
        int[][] tTab_g = gauche.transitionTable;
        ArrayList<Integer>[] eTab_g = gauche.epsilonTransitionTable;
        NDFA droite = build(regExTree.subTrees.get(1));
        int[][] tTab_d = droite.transitionTable;
        ArrayList<Integer>[] eTab_d = droite.epsilonTransitionTable;

        if (regExTree.root==RegEx.CONCAT) return concateCase(tTab_g, tTab_d, eTab_g, eTab_d);
        if (regExTree.root==RegEx.ALTERN) return alternCase(tTab_g, tTab_d, eTab_g, eTab_d);
        return null;
    }
    private NDFA emptyCase(RegExTree regExTree) {
        //IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS transitionTable.length-1
        int[][] tTab = new int[2][N];
        ArrayList<Integer>[] eTab = new ArrayList[2];
        //DUMMY VALUES FOR INITIALIZATION
        for (int i=0;i<tTab.length;i++) for (int col=0;col<N;col++) tTab[i][col]=-1;
        for (int i=0;i<eTab.length;i++) eTab[i]=new ArrayList<>();
        if (regExTree.root!=RegEx.DOT) tTab[0][regExTree.root]=1; //transition regExTree.root from initial state "0" to final state "1"
        else for (int i=0;i<N;i++) tTab[0][i]=1; //transition DOT from initial state "0" to final state "1"
        return new NDFA(tTab,eTab);
    }
    private NDFA concateCase(int[][] tTab_g, int[][] tTab_d,
                             ArrayList<Integer>[] eTab_g, ArrayList<Integer>[] eTab_d) {
        int lg=tTab_g.length, ld=tTab_d.length;
        int[][] tTab = new int[lg+ld][N];
        ArrayList<Integer>[] eTab = new ArrayList[lg+ld];
        //DUMMY VALUES FOR INITIALIZATION
        for (int i=0;i<tTab.length;i++) for (int col=0;col<N;col++) tTab[i][col]=-1;
        for (int i=0;i<eTab.length;i++) eTab[i]=new ArrayList<>();

        eTab[lg-1].add(lg); //epsilon transition from old final state "left" to old initial state "right"

        for (int i=0;i<lg;i++) for (int col=0;col<N;col++) tTab[i][col]=tTab_g[i][col]; //copy old transitions
        for (int i=0;i<lg;i++) eTab[i].addAll(eTab_g[i]); //copy old transitions
        for (int i=lg;i<lg+ld-1;i++) for (int col=0;col<N;col++) if (tTab_d[i-lg][col]!=-1) tTab[i][col]=tTab_d[i-lg][col]+lg; //copy old transitions
        for (int i=lg;i<lg+ld-1;i++) for (int s: eTab_d[i-lg]) eTab[i].add(s+lg); //copy old transitions
        return new NDFA(tTab,eTab);
    }
    private NDFA alternCase(int[][] tTab_g, int[][] tTab_d,
                            ArrayList<Integer>[] eTab_g, ArrayList<Integer>[] eTab_d) {
        int lg=tTab_g.length, ld=tTab_d.length;
        int[][] tTab = new int[2+lg+ld][N];
        ArrayList<Integer>[] eTab = new ArrayList[2+lg+ld];
        //DUMMY VALUES FOR INITIALIZATION
        for (int i=0;i<tTab.length;i++) for (int col=0;col<N;col++) tTab[i][col]=-1;
        for (int i=0;i<eTab.length;i++) eTab[i]=new ArrayList<>();

        eTab[0].add(1); //epsilon transition from new initial state to old initial state
        eTab[0].add(1+lg); //epsilon transition from new initial state to old initial state
        eTab[1+lg-1].add(2+lg+ld-1); //epsilon transition from old final state to new final state
        eTab[1+lg+ld-1].add(2+lg+ld-1); //epsilon transition from old final state to new final state

        for (int i=1;i<1+lg;i++) for (int col=0;col<N;col++) if (tTab_g[i-1][col]!=-1) tTab[i][col]=tTab_g[i-1][col]+1; //copy old transitions
        for (int i=1;i<1+lg;i++) for (int s: eTab_g[i-1]) eTab[i].add(s+1); //copy old transitions
        for (int i=1+lg;i<1+lg+ld-1;i++) for (int col=0;col<N;col++) if (tTab_d[i-1-lg][col]!=-1) tTab[i][col]=tTab_d[i-1-lg][col]+1+lg; //copy old transitions
        for (int i=1+lg;i<1+lg+ld;i++) for (int s: eTab_d[i-1-lg]) eTab[i].add(s+1+lg); //copy old transitions
        return new NDFA(tTab,eTab);
    }
    private NDFA etoileCase(RegExTree regExTree) {
        //IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS transitionTable.length-1
        NDFA fils = build(regExTree.subTrees.get(0));
        int[][] tTab_fils = fils.transitionTable;
        ArrayList<Integer>[] eTab_fils = fils.epsilonTransitionTable;
        int l=tTab_fils.length;
        int[][] tTab = new int[2+l][N];
        ArrayList<Integer>[] eTab = new ArrayList[2+l];
        //DUMMY VALUES FOR INITIALIZATION
        for (int i=0;i<tTab.length;i++) for (int col=0;col<N;col++) tTab[i][col]=-1;
        for (int i=0;i<eTab.length;i++) eTab[i]=new ArrayList<>();

        eTab[0].add(1); //epsilon transition from new initial state to old initial state
        eTab[0].add(2+l-1); //epsilon transition from new initial state to new final state
        eTab[2+l-2].add(2+l-1); //epsilon transition from old final state to new final state
        eTab[2+l-2].add(1); //epsilon transition from old final state to old initial state

        for (int i=1;i<2+l-1;i++) for (int col=0;col<N;col++) if (tTab_fils[i-1][col]!=-1) tTab[i][col]=tTab_fils[i-1][col]+1; //copy old transitions
        for (int i=1;i<2+l-1;i++) for (int s: eTab_fils[i-1]) eTab[i].add(s+1); //copy old transitions
        return new NDFA(tTab,eTab);
    }
    public NDFA getAutomata() {return automata;}
}
class NDFA {
    //IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS transitionTable.length-1
    protected int[][] transitionTable; //ASCII transition
    protected ArrayList<Integer>[] epsilonTransitionTable; //epsilon transition list
    public NDFA(int[][] transitionTable, ArrayList<Integer>[] epsilonTransitionTable) {
        this.transitionTable=transitionTable;
        this.epsilonTransitionTable=epsilonTransitionTable;
    }
    //PRINT THE AUTOMATON TRANSITION TABLE
    public String toString() {
        String result="Initial state: 0\nFinal state: "+(transitionTable.length-1)+"\nTransition list:\n";
        for (int i=0;i<epsilonTransitionTable.length;i++) for (int state: epsilonTransitionTable[i])
            result+="  "+i+" -- epsilon --> "+state+"\n";
        for (int i=0;i<transitionTable.length;i++) for (int col = 0; col< NFA.N; col++)
            if (transitionTable[i][col]!=-1) result+="  "+i+" -- "+(char)col+" --> "+transitionTable[i][col]+"\n";
        return result;
    }

    public int[][] getTransitionTable() {return transitionTable;}
    public ArrayList<Integer>[] getEpsilonTransitionTable() {return epsilonTransitionTable;}
}
