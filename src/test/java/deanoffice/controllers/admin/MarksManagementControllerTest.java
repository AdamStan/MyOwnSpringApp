package deanoffice.controllers.admin;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.services.MarkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

public class MarksManagementControllerTest extends BaseAdminControllersTest {

    @InjectMocks
    private MarksManagementController marksController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marksController).build();
        MarkService markService = new MarkService();
        markService.setMarkRepository(markRepository);
        markService.setStudentRepository(studentRepository);
        markService.setSubjectRepository(subjectRepository);
        markService.setTutorRepository(tutorRepository);
        marksController.setMarkService(markService);
    }

    @Test
    public void testList() throws Exception {
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark());
        marks.add(new Mark());

        when(markRepository.findAll()).thenReturn(marks);
        mockMvc.perform(get("/admin/allmarks"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/marks.html"))
                .andExpect(model().attribute("marks", hasSize(2)));
    }

    @Test
    public void testAddForm() throws Exception {
        mockMvc.perform(get("/admin/marks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/markform.html"));
    }

    @Test
    public void testConfirmAddingMark_correctData2() throws Exception {
        String value = "4.5";
        String studentId = "1";
        String tutorId = "1";
        String subjectName = "subject";

        Tutor tutor = new Tutor();
        Student student = new Student();
        Subject subject = new Subject();

        when(tutorRepository.findById(any())).thenReturn(Optional.of(tutor));
        when(studentRepository.findByIndexNumber(any())).thenReturn(student);
        when(subjectRepository.findByName(any())).thenReturn(subject);

        mockMvc.perform(post("/admin/marks/editconfirm")
                        .param("id", "-1")
                        .param("value", value)
                        .param("studentid", studentId)
                        .param("tutorid", tutorId)
                        .param("subjectname", subjectName)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allmarks"));

        ArgumentCaptor<Mark> boundMark = ArgumentCaptor.forClass(Mark.class);
        verify(markRepository).save(boundMark.capture());
        assertEquals(Double.parseDouble(value), boundMark.getValue().getValue());
        assertEquals(tutor, boundMark.getValue().getTutor());
        assertEquals(student, boundMark.getValue().getStudent());
        assertEquals(subject, boundMark.getValue().getSubject());
    }

    @Test
    public void testConfirmEditingMark_correctData() throws Exception {
        String markId = "1";
        String value = "4.5";
        String studentId = "1";
        String tutorId = "1";
        String subjectName = "subject";

        Tutor tutor = new Tutor();
        Student student = new Student();
        Subject subject = new Subject();
        Mark mark = new Mark();

        when(markRepository.findById(any())).thenReturn(Optional.of(mark));
        when(tutorRepository.findById(any())).thenReturn(Optional.of(tutor));
        when(studentRepository.findByIndexNumber(any())).thenReturn(student);
        when(subjectRepository.findByName(subjectName)).thenReturn(subject);

        mockMvc.perform(post("/admin/marks/editconfirm")
                        .param("id", markId)
                        .param("value", value)
                        .param("studentid", studentId)
                        .param("tutorid", tutorId)
                        .param("subjectname", subjectName)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allmarks"));

        ArgumentCaptor<Mark> boundMark = ArgumentCaptor.forClass(Mark.class);
        verify(markRepository).save(boundMark.capture());
        assertEquals(Double.parseDouble(value), boundMark.getValue().getValue());
        assertEquals(tutor, boundMark.getValue().getTutor());
        assertEquals(student, boundMark.getValue().getStudent());
        assertEquals(subject, boundMark.getValue().getSubject());
    }

    @Test
    public void testDeleteMark() throws Exception {
        Integer id = 1;

        Mark mark = new Mark();
        when(markRepository.findById(id)).thenReturn(Optional.of(mark));

        mockMvc.perform(post("/admin/marks/delete/")
                        .param("id", id.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allmarks"));

        verify(markRepository, times(1)).delete(mark);
    }

    @Test
    public void testShowEditMarkPage() throws Exception {
        int id = 1;
        Mark mark = new Mark();
        when(markRepository.findById(any())).thenReturn(Optional.of(mark));

        mockMvc.perform(get("/admin/marks/edit")
                        .param("id", Integer.toString(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/markform.html"));
    }

}
