package deanoffice.services;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.MarkRepository;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MarkServiceTest {
    @Mock
    protected MarkRepository markRepository;
    @Mock
    protected StudentRepository studentRepository;
    @Mock
    protected SubjectRepository subjectRepository;
    @Mock
    protected TutorRepository tutorRepository;

    private MarkService markService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        markService = new MarkService();
        markService.setMarkRepository(markRepository);
        markService.setTutorRepository(tutorRepository);
        markService.setSubjectRepository(subjectRepository);
        markService.setStudentRepository(studentRepository);
    }

    @Test
    public void testGetObjectToAdd() {
        Mark mark = markService.getObjectToAdd();
        assertEquals(-1, mark.getId().intValue());
        assertNotNull(mark.getStudent());
        assertNotNull(mark.getTutor());
        assertNotNull(mark.getSubject());
        assertNull(mark.getTutor().getId());
        assertNull(mark.getSubject().getName());
    }

    @Test
    public void testGetObjectToEdit() {
        String id = "1";
        Mark mark = new Mark();
        when(markRepository.findById(any())).thenReturn(Optional.of(mark));
        Mark getMark = markService.getObjectToEdit(id);
        assertEquals(mark, getMark);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetObjectToEdit_notExists() {
        String id = "1";
        markService.getObjectToEdit(id);
    }

    @Test
    public void testRemoveObject() {
        String id = "1";
        Mark mark = new Mark();
        when(markRepository.findById(any())).thenReturn(Optional.of(mark));
        markService.removeObject(id);
        verify(markRepository, times(1)).delete(mark);
    }

    @Test
    public void testRemoveObject_markNotFound() {
        String id = "1";
        markService.removeObject(id);
        verify(markRepository, times(0)).delete(any());
    }

    @Test
    public void testGetAll() {
        List<Mark> marks = List.of(new Mark(), new Mark());
        when(markRepository.findAll()).thenReturn(marks);
        List<Mark> fromService = markService.getAll();
        assertEquals(marks.size(), fromService.size());
    }

    @Test
    public void testUpdate() {
        String id = "1";
        Mark mark = new Mark();
        mark.setId(Integer.valueOf(id));
        String newValue = "4.5";
        Student student = new Student();
        Tutor tutor = new Tutor();
        Subject subject = new Subject();
        String subjectName = "subject";

        when(markRepository.findById(Integer.valueOf(id))).thenReturn(Optional.of(mark));
        when(studentRepository.findByIndexNumber(Integer.valueOf(id))).thenReturn(student);
        when(tutorRepository.findById(Integer.valueOf(id))).thenReturn(Optional.of(tutor));
        when(subjectRepository.findByName(subjectName)).thenReturn(subject);
        markService.update(id, newValue, id, id, subjectName);
        verify(markRepository, times(1)).save(any());
    }

    @Test
    public void testCreateNew() {
        String id = "1";
        String newValue = "4.5";
        Student student = new Student();
        Tutor tutor = new Tutor();
        Subject subject = new Subject();
        String subjectName = "subject";

        when(studentRepository.findByIndexNumber(Integer.valueOf(id))).thenReturn(student);
        when(tutorRepository.findById(Integer.valueOf(id))).thenReturn(Optional.of(tutor));
        when(subjectRepository.findByName(subjectName)).thenReturn(subject);
        markService.update(id, newValue, id, id, subjectName);
        verify(markRepository, times(1)).save(any());
    }
}
