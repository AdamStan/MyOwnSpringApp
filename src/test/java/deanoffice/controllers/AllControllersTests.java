package deanoffice.controllers;

import deanoffice.controllers.admin.FacultiesManagementControllerTest;
import deanoffice.controllers.admin.MarksManagementControllerTest;
import deanoffice.controllers.admin.TutorsManagementControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MarksManagementControllerTest.class,
        FacultiesManagementControllerTest.class,
        TutorsManagementControllerTest.class,
})
public class AllControllersTests {
}
