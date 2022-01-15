package deanoffice.controllers.admin;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.controllers.BaseControllersTest;

public class AdminManyToManyControllerTest extends BaseControllersTest {

    @InjectMocks
    private AdminManyToManyController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAllStudentToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testAllTutorToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddStudentToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testConfirmAddStudentToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteStudentToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddTutorToSubjectt() {
        fail("Not yet implemented");
    }

    @Test
    public void testConfirmAddTutorToSubject() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteTutorToSubject() {
        fail("Not yet implemented");
    }

}
