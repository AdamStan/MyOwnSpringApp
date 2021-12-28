package deanoffice.controllers.admin;

import deanoffice.controllers.BaseControllersTest;
import deanoffice.entities.Faculty;
import deanoffice.entities.Subject;
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
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SubjectsManagementControllerTest extends BaseControllersTest {
    @InjectMocks
    private SubjectsManagementController subjectsController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectsController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());

        when(subjectRepository.findAll()).thenReturn(subjects);
        mockMvc.perform(get("/admin/allsubjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/subjects.html"))
                .andExpect(model().attribute("subjects", hasSize(2)));
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(get("/admin/subjects/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/addsubject.html"));
    }

    @Test
    public void testEditPage() throws Exception {
        int id = 1;
        Subject subject = new Subject();
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        mockMvc.perform(get("/admin/subjects/edit")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/editsubject.html"));
    }

    @Test
    public void testDeleteSubject() throws Exception {
        int id = 1;

        Subject subject = new Subject();
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        mockMvc.perform(post("/admin/subjects/delete")
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allsubjects"));

        verify(subjectRepository, times(1)).delete(subject);
    }

    @Test
    public void testConfirmAddingStudent_correctData() throws Exception {
        String name = "subject name";
        String facultyId = "1";

        Faculty faculty = new Faculty("faculty_name", "description");
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(post("/admin/subjects/addconfirm")
                        .param("name", name)
                        .param("facultyid", facultyId)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allsubjects"));

        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        assertEquals(name, subjectArgumentCaptor.getValue().getName());
        assertEquals(faculty.getName(), subjectArgumentCaptor.getValue().getFaculty().getName());
    }

    @Test
    public void testConfirmEditingTutors_correctData() throws Exception {
        Integer id = 1;
        String name = "subject name";
        String facultyId = "1";

        Subject subject = new Subject();
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        Faculty faculty = new Faculty("faculty_name", "description");
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(post("/admin/subjects/editconfirm")
                        .param("id", id.toString())
                        .param("name", name)
                        .param("facultyid", facultyId)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allsubjects"));

        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        assertEquals(name, subjectArgumentCaptor.getValue().getName());
        assertEquals(faculty.getName(), subjectArgumentCaptor.getValue().getFaculty().getName());
    }
}
