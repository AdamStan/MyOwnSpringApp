package deanoffice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.security.UserService;

public class UsersThemselvesManagementControllerTest
        extends BaseControllersTest {
    @InjectMocks
    private UsersThemselvesManagementController controller;
    @Mock
    private UserService table;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/admin/allusers")).andExpect(status().isOk())
                .andExpect(view().name("/admin/users.html"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(get("/admin/allusers/deleted"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/users.html"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testEditUser() throws Exception {
        mockMvc.perform(get("/admin/allusers/edituser"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/users.html"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testConfirmEditUser() throws Exception {
        mockMvc.perform(post("/admin/allusers/confirmedit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/users.html"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testAddNewUserForm() throws Exception {
        mockMvc.perform(get("/admin/addnewuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/adduser.html"));
    }

    @Test
    public void testConfirmUserAdding() throws Exception {
        mockMvc.perform(post("/admin/allusers/confirm"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/users.html"))
                .andExpect(model().attributeExists("users"));
    }

}
