import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RegExTreeTest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    @Test
    void testRegExTreeClone() {
        RegExTree clone = RegExTree.cloneTree(tree);
        assertNotEquals(tree, clone);
        assertEquals(tree.root, clone.root);
    }
    @Test
    void testToString() {
        assertEquals(tree.toString(), "|(a,.(b,*(c)))");
    }
}
