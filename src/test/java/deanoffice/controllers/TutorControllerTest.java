package deanoffice.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import deanoffice.entities.Mark;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.services.MarkService;

public class TutorControllerTest extends BaseControllersTest {

    @Mock
    private MarkService markService;

    @InjectMocks
    private TutorController tutorController;

    private SubjectsAndMarks info;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tutorController).build();

        info = prepareSubjectsWithMarks();
        Tutor tutor = new Tutor();
        tutor.setId(123);
        tutor.setSubjects(info.subjects);
        when(tutorRepository.findByUsername(any())).thenReturn(Optional.of(tutor));

    }

    @Test
    public void testTutorMarks() throws Exception {
        mockMvc.perform(get("/tutor/marks"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/marks.html"))
                .andExpect(model().attributeExists("marks"))
                .andExpect(model().attribute("marks", hasSize(info.allMarks.size())));
    }

    @Test
    public void testTutorSubjects() throws Exception {
        mockMvc.perform(get("/tutor/subjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/subjects.html"))
                .andExpect(model().attributeExists("subjects"))
                .andExpect(model().attribute("subjects", hasSize(info.subjects.size())));
    }

    @Test
    public void testTutorMarksAdd() throws Exception {
        mockMvc.perform(get("/tutor/marks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/addmark.html"))
                .andExpect(model().attributeExists("subjects"))
                .andExpect(model().attribute("tutorid", 123))
                .andExpect(model().attribute("subjects", hasSize(info.subjects.size())));
    }

    @Test
    public void testTutorMarksAddconfirm() throws Exception {
        mockMvc.perform(post("/tutor/marks/addconfirm").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/marks.html"));
    }

    @Test
    public void testTutorMarksDelete() throws Exception {
        mockMvc.perform(post("/tutor/marks/delete").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/marks.html"));
    }

    @Test
    public void testTutorMarksEdit() throws Exception {
        Mark mark = new Mark(3.5);
        when(markService.getObjectToEdit(any())).thenReturn(mark);
        mockMvc.perform(get("/tutor/marks/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/editmark.html"))
                .andExpect(model().attributeExists("subjects"))
                .andExpect(model().attribute("tutorid", 123))
                .andExpect(model().attribute("subjects", hasSize(info.subjects.size())))
                .andExpect(model().attribute("mark", mark));
    }

    @Test
    public void testTutorMarksEditconfirm() throws Exception {
        SubjectsAndMarks info = prepareSubjectsWithMarks();
        Tutor tutor = new Tutor();
        tutor.setSubjects(info.subjects);
        when(tutorRepository.findByUsername(any())).thenReturn(Optional.of(tutor));
        mockMvc.perform(post("/tutor/marks/editconfirm").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tutor/marks.html"));
    }

    private static SubjectsAndMarks prepareSubjectsWithMarks() {
        Set<Subject> subjects = new HashSet<>();
        Set<Mark> allMarks = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Subject bulk = new Subject();
            Set<Mark> marks = new HashSet<>();
            marks.add(new Mark());
            marks.add(new Mark());
            bulk.setMarks(marks);
            allMarks.addAll(marks);
            subjects.add(bulk);
        }
        return new SubjectsAndMarks(subjects, allMarks);
    }

    private static class SubjectsAndMarks {
        private final Set<Subject> subjects;
        private final Set<Mark> allMarks;

        SubjectsAndMarks(Set<Subject> subjects, Set<Mark> allMarks) {
            this.subjects = subjects;
            this.allMarks = allMarks;
        }
    }

}
