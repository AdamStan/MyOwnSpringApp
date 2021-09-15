package deanoffice.controllers.admin;

import deanoffice.repositories.*;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

public class BaseAdminControllersTest {
    @Mock
    protected MarkRepository markRepository;
    @Mock
    protected StudentRepository studentRepository;
    @Mock
    protected SubjectRepository subjectRepository;
    @Mock
    protected TutorRepository tutorRepository;
    @Mock
    protected FacultyRepository facultyRepository;

    protected MockMvc mockMvc;

}
