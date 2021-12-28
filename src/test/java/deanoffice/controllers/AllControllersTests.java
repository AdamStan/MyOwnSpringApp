package deanoffice.controllers;

import deanoffice.controllers.admin.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MarksManagementControllerTest.class,
        FacultiesManagementControllerTest.class,
        TutorsManagementControllerTest.class,
        StudentsManagementControllerTest.class,
        SubjectsManagementControllerTest.class,
        StudentControllerTest.class
})
public class AllControllersTests {
}
