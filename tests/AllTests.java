import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RegExTreeTest.class, NFATest.class, DFATest.class, EgrepTest.class})
public class AllTests {
}
