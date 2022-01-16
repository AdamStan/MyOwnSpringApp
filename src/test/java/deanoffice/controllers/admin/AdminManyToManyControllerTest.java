package deanoffice.controllers.admin;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.controllers.BaseControllersTest;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;

public class AdminManyToManyControllerTest extends BaseControllersTest {

    @InjectMocks
    private AdminManyToManyController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAllStudentToSubject() throws Exception {
        List<Subject> subjects = List.of(new Subject(), new Subject());
        subjects.forEach(
                s -> s.setStudents(Set.of(new Student(), new Student())));
        when(subjectRepository.findAll()).thenReturn(subjects);

        mockMvc.perform(get("/admin/studenttosubject/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/studenttosubject.html"))
                .andExpect(model().attribute("couples", hasSize(4)));
    }

    @Test
    public void testAllTutorToSubject() throws Exception {
        List<Subject> subjects = List.of(new Subject(), new Subject());
        subjects.forEach(s -> s.setTutors(Set.of(new Tutor(), new Tutor())));
        when(subjectRepository.findAll()).thenReturn(subjects);

        mockMvc.perform(get("/admin/tutortosubject/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/tutortosubject.html"))
                .andExpect(model().attribute("couples", hasSize(4)));
    }

    @Test
    public void testAddStudentToSubject() throws Exception {
        mockMvc.perform(get("/admin/studenttosubject/add"))
                .andExpect(status().isOk()).andExpect(
                        view().name("/admin/adding/addstudenttosubject.html"));
    }

    @Test
    public void testConfirmAddStudentToSubject() throws Exception {
        final Student student = new Student();
        student.setSubjects(new HashSet<Subject>());
        final Subject subject = new Subject();
        subject.setStudents(new HashSet<Student>());
        when(subjectRepository.findById(anyInt()))
                .thenReturn(Optional.of(subject));
        when(studentRepository.findByIndexNumber(anyInt())).thenReturn(student);

        mockMvc.perform(post("/admin/studenttosubject/addconfirm")
                .param("subjectid", "12").param("studentid", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/studenttosubject.html"));

        verify(subjectRepository).save(subject);
        verify(studentRepository).save(student);
    }

    @Test
    public void testDeleteStudentToSubject() throws Exception {
        final Student student = new Student();
        final Subject subject = new Subject();
        Set<Student> students = new HashSet<>();
        Set<Subject> subjects = new HashSet<>();
        subjects.add(subject);
        students.add(student);
        student.setSubjects(subjects);
        subject.setStudents(students);

        when(subjectRepository.findById(anyInt()))
                .thenReturn(Optional.of(subject));
        when(studentRepository.findByIndexNumber(anyInt())).thenReturn(student);

        mockMvc.perform(post("/admin/studenttosubject/delete")
                .param("parameter", "12!123")).andExpect(status().isOk())
                .andExpect(view().name("/admin/studenttosubject.html"));

        verify(subjectRepository).save(subject);
        verify(studentRepository).save(student);
        assertTrue(student.getSubjects().isEmpty());
        assertTrue(subject.getStudents().isEmpty());
    }

    @Test
    public void testAddTutorToSubjectt() throws Exception {
        mockMvc.perform(get("/admin/tutortosubject/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/addtutortosubject.html"));
    }

    @Test
    public void testConfirmAddTutorToSubject() throws Exception {
        final Tutor tutor = new Tutor();
        tutor.setSubjects(new HashSet<Subject>());
        final Subject subject = new Subject();
        subject.setTutors(new HashSet<Tutor>());
        when(subjectRepository.findById(anyInt()))
                .thenReturn(Optional.of(subject));
        when(tutorRepository.findById(anyInt())).thenReturn(Optional.of(tutor));

        mockMvc.perform(post("/admin/tutortosubject/addconfirm")
                .param("subjectid", "12").param("tutorid", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/tutortosubject.html"));

        verify(subjectRepository).save(subject);
        verify(tutorRepository).save(tutor);
    }

    @Test
    public void testDeleteTutorToSubject() throws Exception {
        final Tutor tutor = new Tutor();
        final Subject subject = new Subject();
        Set<Tutor> students = new HashSet<>();
        Set<Subject> subjects = new HashSet<>();
        subjects.add(subject);
        students.add(tutor);
        tutor.setSubjects(subjects);
        subject.setTutors(students);

        when(subjectRepository.findById(anyInt()))
                .thenReturn(Optional.of(subject));
        when(tutorRepository.findById(anyInt())).thenReturn(Optional.of(tutor));

        mockMvc.perform(post("/admin/tutortosubject/delete")
                .param("parameter", "12!123")).andExpect(status().isOk())
                .andExpect(view().name("/admin/tutortosubject.html"));

        verify(subjectRepository).save(subject);
        verify(tutorRepository).save(tutor);
        assertTrue(tutor.getSubjects().isEmpty());
        assertTrue(subject.getTutors().isEmpty());
    }

}
