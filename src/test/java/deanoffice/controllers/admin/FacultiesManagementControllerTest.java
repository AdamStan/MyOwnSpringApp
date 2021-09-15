package deanoffice.controllers.admin;

import deanoffice.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FacultiesManagementControllerTest extends BaseAdminControllersTest {
    @InjectMocks
    private FacultiesManagementController facultiesController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(facultiesController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty());
        faculties.add(new Faculty());

        when(facultyRepository.findAll()).thenReturn(faculties);
        mockMvc.perform(get("/admin/allfaculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/faculties.html"))
                .andExpect(model().attribute("faculties", hasSize(2)));
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(get("/admin/addnewfaculty"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/facultyform.html"));
    }

    @Test
    public void testConfirmAddingFaculty_correctData() throws Exception {
        String name = "name";
        String description = "short description";

        mockMvc.perform(post("/admin/faculty/confirm")
                        .param("name", name)
                        .param("description", description)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allfaculties"));

        ArgumentCaptor<Faculty> facultyArgumentCaptor = ArgumentCaptor.forClass(Faculty.class);
        verify(facultyRepository).save(facultyArgumentCaptor.capture());
        assertEquals(name, facultyArgumentCaptor.getValue().getName());
        assertEquals(description, facultyArgumentCaptor.getValue().getDescription());
        assertNull(facultyArgumentCaptor.getValue().getId());
    }

    @Test
    public void testConfirmEditingFaculty_correctData() throws Exception {
        String id = "1";
        String name = "name";
        String description = "short description";

        Faculty faculty = new Faculty();
        faculty.setId(Integer.valueOf(id));
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(post("/admin/faculty/confirm")
                        .param("id", id)
                        .param("name", name)
                        .param("description", description)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allfaculties"));

        ArgumentCaptor<Faculty> facultyArgumentCaptor = ArgumentCaptor.forClass(Faculty.class);
        verify(facultyRepository).save(facultyArgumentCaptor.capture());
        assertEquals(name, facultyArgumentCaptor.getValue().getName());
        assertEquals(description, facultyArgumentCaptor.getValue().getDescription());
        assertEquals(faculty.getId(), facultyArgumentCaptor.getValue().getId());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Integer id = 1;

        Faculty faculty = new Faculty();
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/admin/faculty/delete")
                        .param("id", id.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allfaculties"));

        verify(facultyRepository, times(1)).delete(faculty);
    }

    @Test
    public void testEditPage() throws Exception {
        int id = 1;
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/admin/faculty/editfaculty")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/facultyform.html"));
    }
}
