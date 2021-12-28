package deanoffice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;

public class StudentControllerTest extends BaseControllersTest {

    @InjectMocks
    private StudentController studentController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testStudentMarks() throws Exception {
        Set<Mark> marks = new HashSet<>();
        marks.add(new Mark());
        Student student = new Student();
        student.setMarks(marks);
        when(studentRepository.findByUsername(any())).thenReturn(student);
        mockMvc.perform(get("/student/marks"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/studentsMarks.html"))
                .andExpect(model().attribute("marks", marks));
    }

    @Test
    public void testStudentSubjects() throws Exception {
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject());
        Student student = new Student();
        student.setSubjects(subjects);
        when(studentRepository.findByUsername(any())).thenReturn(student);
        mockMvc.perform(get("/student/subjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/studentsSubjects.html"))
                .andExpect(model().attribute("subjects", subjects));
    }

    @Test
    public void testStudentSubjectWhenStudentNotFound() throws Exception {
        mockMvc.perform(get("/student/subjects"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testStudentMarksWhenStudentNotFound() throws Exception {
        mockMvc.perform(get("/student/marks"))
                .andExpect(status().is4xxClientError());
    }

}
