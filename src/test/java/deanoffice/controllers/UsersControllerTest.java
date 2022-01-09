package deanoffice.controllers;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.mocks.MockUser;
import deanoffice.security.UserSecurityProvider;

public class UsersControllerTest extends BaseControllersTest {

    @InjectMocks
    private UsersController usersController;
    @Spy
    private UserSecurityProvider provider;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
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
    public void testStudentUserOptions() {
        fail("Not implemented yet");
    }

    @Test
    public void testTutorUserOptions() {
        fail("Not implemented yet");
    }

    @Test
    public void testAdminUserOptions() {
        fail("Not implemented yet");
    }

    @Test
    public void testNonUserOptions() {
        fail("Not implemented yet");
    }

    @Test
    public void testConfirmStudent() {
        fail("Not implemented yet");
    }

    @Test
    public void testConfirmStudentException() {
        fail("Not implemented yet");
    }

    @Test
    public void testConfirmTutor() {
        fail("Not implemented yet");
    }

    @Test
    public void testConfimrTutorException() {
        fail("Not implemented yet");
    }

    @Test
    public void testConfirmAdmin() {
        fail("Not implemented yet");
    }

    public void testConfirmAdminException() {
        fail("Not implemented yet");
    }

}
