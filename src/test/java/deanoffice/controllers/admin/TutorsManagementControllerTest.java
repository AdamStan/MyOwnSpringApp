package deanoffice.controllers.admin;

import deanoffice.controllers.BaseControllersTest;
import deanoffice.entities.Faculty;
import deanoffice.entities.Tutor;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TutorsManagementControllerTest extends BaseControllersTest {
    @InjectMocks
    private TutorsManagementController tutorsController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tutorsController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Tutor> tutors = new ArrayList<>();
        tutors.add(new Tutor());
        tutors.add(new Tutor());

        when(tutorRepository.findAll()).thenReturn(tutors);
        mockMvc.perform(get("/admin/alltutors"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/tutors.html"))
                .andExpect(model().attribute("tutors", hasSize(2)));
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(get("/admin/tutors/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/addtutor.html"));
    }

    @Test
    public void testEditPage() throws Exception {
        int id = 1;
        Tutor tutor = new Tutor();
        when(tutorRepository.findById(id)).thenReturn(Optional.of(tutor));

        mockMvc.perform(get("/admin/tutors/edit")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/edittutor.html"));
    }

    @Test
    public void testDeleteTutor() throws Exception {
        Integer id = 1;

        Tutor tutor = new Tutor();
        when(tutorRepository.findById(id)).thenReturn(Optional.of(tutor));

        mockMvc.perform(post("/admin/tutors/delete")
                        .param("id", id.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/alltutors"));

        verify(tutorRepository, times(1)).delete(tutor);
    }

    @Test
    public void testConfirmAddingTutor_correctData() throws Exception {
        String name = "John";
        String surname = "Nowak";
        String username = "user_tutor1";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "31A";
        String numberOfFlat = "23";
        String facultyid = "1";
        String whenStarted = "1990-08-12";

        Faculty faculty = new Faculty();
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(post("/admin/tutors/addconfirm")
                        .param("name", name)
                        .param("surname", surname)
                        .param("username", username)
                        .param("city", city)
                        .param("street", street)
                        .param("numberofbuilding", numberOfBuilding)
                        .param("numberofflat", numberOfFlat)
                        .param("facultyid", facultyid)
                        .param("whenStarted", whenStarted)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/alltutors"));

        ArgumentCaptor<Tutor> tutorArgumentCaptor = ArgumentCaptor.forClass(Tutor.class);
        verify(tutorRepository).save(tutorArgumentCaptor.capture());
        assertEquals(name, tutorArgumentCaptor.getValue().getName());
        assertEquals(street, tutorArgumentCaptor.getValue().getStreet());
        assertNull(tutorArgumentCaptor.getValue().getId());
    }

    @Test
    public void testConfirmEditingTutors_correctData() throws Exception {
        String id = "1";
        String name = "John";
        String surname = "Nowak";
        String username = "user_tutor1";
        String city = "city";
        String street = "street";
        String numberOfBuilding = "31A";
        String numberOfFlat = "23";
        String facultyid = "1";
        String whenStarted = "1990-08-12";
        String whenFinnished = "2024-09-30";

        Faculty faculty = new Faculty();
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        Tutor tutor = new Tutor();
        when(tutorRepository.findById(any())).thenReturn(Optional.of(tutor));

        mockMvc.perform(post("/admin/tutors/editconfirm")
                        .param("id", id)
                        .param("name", name)
                        .param("surname", surname)
                        .param("username", username)
                        .param("city", city)
                        .param("street", street)
                        .param("numberofbuilding", numberOfBuilding)
                        .param("numberofflat", numberOfFlat)
                        .param("facultyid", facultyid)
                        .param("whenStarted", whenStarted)
                        .param("whenFinnished", whenFinnished)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/alltutors"));

        ArgumentCaptor<Tutor> tutorArgumentCaptor = ArgumentCaptor.forClass(Tutor.class);
        verify(tutorRepository).save(tutorArgumentCaptor.capture());
        assertEquals(name, tutorArgumentCaptor.getValue().getName());
        assertEquals(street, tutorArgumentCaptor.getValue().getStreet());
    }

}