package deanoffice.controllers.admin;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.services.MarkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MarksManagementControllerTest extends BaseAdminControllersTest {

    @InjectMocks
    private MarksManagementController marksController;
    @Spy
    private MarkService markService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marksController).build();
        doNothing().when(markService).update(any(), any(), any(), any(), any());
        doNothing().when(markService).removeObject(any());
        marksController.setMarkService(markService);
    }

    @Test
    public void testList() throws Exception {
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark());
        marks.add(new Mark());

        doReturn(marks).when(markService).getAll();
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
        verify(markService, times(1)).update(markId, value, studentId, tutorId, subjectName);
    }

    @Test
    public void testDeleteMark() throws Exception {
        int id = 1;

        mockMvc.perform(post("/admin/marks/delete/")
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/allmarks"));

        verify(markService, times(1)).removeObject(String.valueOf(id));
    }

    @Test
    public void testShowEditMarkPage() throws Exception {
        int id = 1;
        Mark mark = new Mark();
        doReturn(mark).when(markService).getObjectToEdit(String.valueOf(id));
        mockMvc.perform(get("/admin/marks/edit")
                        .param("id", Integer.toString(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adding/markform.html"));
    }

}
