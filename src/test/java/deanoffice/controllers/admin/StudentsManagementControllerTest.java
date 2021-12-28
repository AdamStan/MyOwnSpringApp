package deanoffice.controllers.admin;

import deanoffice.controllers.BaseControllersTest;
import deanoffice.entities.Faculty;
import deanoffice.entities.Student;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentsManagementControllerTest extends BaseControllersTest {
    @InjectMocks
    private StudentsManagementController studentsController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(get("/admin/allstudents"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/students.html"))
                .andExpect(model().attribute("students", hasSize(2)));
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(get("/admin/students/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/addstudent.html"));
    }

    @Test
    public void testEditPage() throws Exception {
        int id = 1;
        Student student = new Student();
        when(studentRepository.findByIndexNumber(id)).thenReturn(student);

        mockMvc.perform(get("/admin/students/edit")
                        .param("indexNumber", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/editstudent.html"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Integer id = 1;

        Student student = new Student();
        when(studentRepository.findByIndexNumber(id)).thenReturn(student);

        mockMvc.perform(get("/admin/students/delete")
                        .param("indexNumber", id.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allstudents"));

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    public void testConfirmAddingStudent_correctData() throws Exception {
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

        mockMvc.perform(post("/admin/students/addconfirm")
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
                .andExpect(view().name("redirect:/admin/allstudents"));

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        assertEquals(name, studentCaptor.getValue().getName());
        assertEquals(street, studentCaptor.getValue().getStreet());
    }

    @Test
    public void testConfirmEditingTutors_correctData() throws Exception {
        String indexNumber = "1";
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
        Student student = new Student();
        when(studentRepository.findByIndexNumber(any())).thenReturn(student);

        mockMvc.perform(post("/admin/students/editconfirm")
                        .param("indexNumber", indexNumber)
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
                .andExpect(view().name("redirect:/admin/allstudents"));

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        assertEquals(name, studentCaptor.getValue().getName());
        assertEquals(street, studentCaptor.getValue().getStreet());
    }
}