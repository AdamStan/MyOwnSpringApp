package deanoffice.repositories;

import deanoffice.entities.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlobalRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Before
    public void setUp(){
        Mark mark = new Mark(3.5);
        mark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        Address addressForStudent = new Address("Tomaszow Mazowiecki", "Wesola", "38", "");
        addressRepository.save(addressForStudent);

        Faculty faculty = new Faculty("WEEIA", "Dlugi opis weeii");
        facultyRepository.save(faculty);

        Student student = new Student("Adam", "Jestem", addressForStudent);
        mark.setStudent(student);

        Subject subject = new Subject("Podstawy Programowania 2", faculty);
        mark.setSubject(subject);
        Tutor tutor = new Tutor();
        tutor.setName("Jacek"); tutor.setSurname("Placek");
        tutor.setFaculty(faculty);
        Set<Subject> subjects = new HashSet<Subject>();
        subjects.add(subject);
        tutor.setSubjects(subjects);
        mark.setTutor(tutor);
        markRepository.save(mark);
    }
    @Test
    public void testFetchDataForMark(){
        Iterable<Mark> marks = markRepository.findAll();
        for(Mark m : marks){
            Assert.assertNotNull(m.getId());
            System.out.println(m);
        }
    }
    @Test
    public void testFetchDataForStudent(){

    }
    @Test
    public void testFetchDataForSubject(){

    }
    @Test
    public void testFetchDataForTutor(){

    }

}
