package deanoffice;

import deanoffice.controllers.AllControllersTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllControllersTests.class,
})
public class AllTests {
}
