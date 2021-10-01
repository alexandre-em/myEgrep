public class NFA {
    final static int M = 257;
    private RegExTree regExTree;
    private int[][] nfa = new int [M][M];
    private int[] epsilons = new int[M+1];
    public NFA(RegExTree regExTree) {
        this.regExTree = regExTree;
    }
    void initialize() {
        for(int i=0; i<M; i++) {
            for(int j=0; j<M; j++) nfa[i][j]=-1;
        }
    }

}