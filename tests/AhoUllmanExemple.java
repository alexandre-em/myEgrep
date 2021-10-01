import java.util.ArrayList;

public class AhoUllmanExemple {
    static final int CONCAT = 0xC04CA7;
    static final int ETOILE = 0xE7011E;
    static final int ALTERN = 0xA17E54;

    public static RegExTree exampleAhoUllman() {
        RegExTree a = new RegExTree((int)'a', new ArrayList<>());
        RegExTree b = new RegExTree((int)'b', new ArrayList<>());
        RegExTree c = new RegExTree((int)'c', new ArrayList<>());
        ArrayList<RegExTree> subTrees = new ArrayList<>();
        subTrees.add(c);
        RegExTree cEtoile = new RegExTree(ETOILE, subTrees);
        subTrees = new ArrayList<>();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTree dotBCEtoile = new RegExTree(CONCAT, subTrees);
        subTrees = new ArrayList<>();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTree(ALTERN, subTrees);
    }
}
