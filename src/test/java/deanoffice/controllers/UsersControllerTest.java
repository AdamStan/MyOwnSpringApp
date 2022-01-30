package deanoffice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.entities.Student;
import deanoffice.entities.Tutor;
import deanoffice.mocks.MockUser;
import deanoffice.security.User;
import deanoffice.security.UserSecurityProvider;
import deanoffice.security.UserService;

public class UsersControllerTest extends BaseControllersTest {

    @InjectMocks
    private UsersController usersController;
    @Mock
    private UserSecurityProvider provider;
    @Mock
    private UserService users;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        when(users.findUser(anyString())).thenReturn(new User());
    }

    @Test
    public void testWhoDidIt() throws Exception {
        mockMvc.perform(get("/whodidit")).andExpect(status().isOk())
                .andExpect(view().name("about.html"));
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("home.html"));
        mockMvc.perform(get("/home")).andExpect(status().isOk())
                .andExpect(view().name("home.html"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }

    @Test
    public void testHelloAdmin() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.ADMIN));
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(view().name("admin/helloAdmin.html"));
    }

    @Test
    public void testHelloTutor() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.TUTOR));
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(view().name("tutor/helloTutor.html"));
    }

    @Test
    public void testHelloStudent() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.STUDENT));
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(view().name("student/helloStudent.html"));
    }

    @Test
    public void testHelloNullAuthentication() throws Exception {
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(view().name("hello.html"));
    }

    @Test
    public void testStudentUserOptions() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.STUDENT));
        when(studentRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(new Student()));
        mockMvc.perform(get("/useroptions")).andExpect(status().isOk())
                .andExpect(view().name("student/myAccount.html"));
    }

    @Test
    public void testTutorUserOptions() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.TUTOR));
        when(tutorRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(new Tutor()));
        mockMvc.perform(get("/useroptions")).andExpect(status().isOk())
                .andExpect(view().name("tutor/myAccount.html"));
    }

    @Test
    public void testAdminUserOptions() throws Exception {
        when(provider.getCurrentSecurityUser())
                .thenReturn(Optional.of(MockUser.ADMIN));
        mockMvc.perform(get("/useroptions")).andExpect(status().isOk())
                .andExpect(view().name("admin/myAccount.html"));
    }

    @Test
    public void testNonUserOptions() throws Exception {
        mockMvc.perform(get("/useroptions")).andExpect(status().isOk())
                .andExpect(view().name("hello.html"));
    }

    @Test
    public void testConfirmStudent() throws Exception {
        String id = "12345";
        String name = "name";
        String surname = "surname";
        String username = "username";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "numberofbuilding";
        String numberOfFlat = "numberofflat";
        String password = "password";
        when(studentRepository.findByIndexNumber(any()))
                .thenReturn(new Student());
//        when(table.findUserByUsername(anyString())).thenReturn(new User());

        mockMvc.perform(post("/useroptions/student").param("indexNumber", id)
                .param("name", name).param("surname", surname)
                .param("username", username).param("city", city)
                .param("street", street)
                .param("numberofbuilding", numberOfBuilding)
                .param("numberofflat", numberOfFlat)
                .param("password", password))
                .andExpect(view().name("/informlogout.html"));
    }

    @Test(expected = Exception.class)
    public void testConfirmStudentUserNameInUse() throws Exception {
        String id = "12345";
        String name = "name";
        String surname = "surname";
        String username = "username";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "numberofbuilding";
        String numberOfFlat = "numberofflat";
        String password = "password";
        when(studentRepository.findByIndexNumber(any()))
                .thenReturn(new Student());
        when(users.findUser(anyString())).thenReturn(new User("d", "r1"));

        mockMvc.perform(post("/useroptions/student").param("indexNumber", id)
                .param("name", name).param("surname", surname)
                .param("username", username).param("city", city)
                .param("street", street)
                .param("numberofbuilding", numberOfBuilding)
                .param("numberofflat", numberOfFlat)
                .param("password", password));
    }

    @Test
    public void testConfirmTutor() throws Exception {
        String id = "12345";
        String name = "name";
        String surname = "surname";
        String username = "username";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "numberofbuilding";
        String numberOfFlat = "numberofflat";
        String password = "password";
        when(tutorRepository.findById(any()))
                .thenReturn(Optional.of(new Tutor()));

        mockMvc.perform(
                post("/useroptions/tutor").param("id", id).param("name", name)
                        .param("surname", surname).param("username", username)
                        .param("city", city).param("street", street)
                        .param("numberofbuilding", numberOfBuilding)
                        .param("numberofflat", numberOfFlat)
                        .param("password", password))
                .andExpect(view().name("/informlogout.html"));
    }

    @Test(expected = Exception.class)
    public void testConfirmTutorUserNameIsInUse() throws Exception {
        String id = "12345";
        String name = "name";
        String surname = "surname";
        String username = "username";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "numberofbuilding";
        String numberOfFlat = "numberofflat";
        String password = "password";

        final User user = new User("b", "r1");
        when(tutorRepository.findById(any()))
                .thenReturn(Optional.of(new Tutor()));
        when(users.findUser(anyString())).thenReturn(user);

        mockMvc.perform(
                post("/useroptions/tutor").param("id", id).param("name", name)
                        .param("surname", surname).param("username", username)
                        .param("city", city).param("street", street)
                        .param("numberofbuilding", numberOfBuilding)
                        .param("numberofflat", numberOfFlat)
                        .param("password", password))
                .andExpect(view().name("/informlogout.html"));
    }

    @Test
    public void testConfirmAdmin() throws Exception {
        String username = "username";
        String password = "city";
        String oldusername = "street";
        String role = "numberofbuilding";
        String enabled = "numberofflat";
        when(users.loadUserByUsername(anyString())).thenReturn(new User());

        mockMvc.perform(post("/useroptions/admin").param("username", username)
                .param("password", password).param("oldusername", oldusername)
                .param("role", role).param("enabled", enabled))
                .andExpect(view().name("/informlogout.html"));
    }

    @Test(expected = Exception.class)
    public void testConfirmAdminUserNameIsInUse() throws Exception {
        String username = "username";
        String password = "city";
        String oldusername = "street";
        String role = "numberofbuilding";
        String enabled = "numberofflat";
        when(users.findUser(anyString())).thenReturn(new User("d", "r1"));

        mockMvc.perform(post("/useroptions/admin").param("username", username)
                .param("password", password).param("oldusername", oldusername)
                .param("role", role).param("enabled", enabled))
                .andExpect(view().name("/informlogout.html"));
    }

}
