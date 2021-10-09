import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RegExTreeTest {
    RegExTree tree = AhoUllmanExemple.exampleAhoUllman();
    @Test
    public void testRegExTreeClone() {
        RegExTree clone = RegExTree.cloneTree(tree);
        assertNotEquals(tree, clone);
        assertEquals(tree.root, clone.root);
    }
    @Test
    public void testToString() {
        assertEquals(tree.toString(), "|(a,.(b,*(c)))");
    }
}
