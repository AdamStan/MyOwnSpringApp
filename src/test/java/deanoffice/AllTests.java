package deanoffice;

import deanoffice.controllers.AllControllersTests;
import deanoffice.services.AllServicesTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllControllersTests.class,
        AllServicesTests.class
})
public class AllTests {
}
