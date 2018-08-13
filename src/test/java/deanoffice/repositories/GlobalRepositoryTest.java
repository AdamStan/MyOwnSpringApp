package deanoffice.repositories;

import deanoffice.entities.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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

    public void setUp(){
        Mark mark = new Mark(3.5);
        mark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        Address addressForStudent = new Address("Tomaszow Mazowiecki", "Wesola", "38", "");
        addressRepository.save(addressForStudent);

        Faculty faculty = new Faculty("WEEIA", "Dlugi opis weeii");
        facultyRepository.save(faculty);

        Subject subject = new Subject("Podstawy Programowania 2", faculty);
        mark.setSubject(subject);
        subjectRepository.save(subject);

        Student student = new Student("Adam", "Jestem", addressForStudent);
        studentRepository.save(student);
        mark.setStudent(student);

        Tutor tutor = new Tutor();
        tutor.setName("Jacek"); tutor.setSurname("Placek");
        tutor.setFaculty(faculty);
        Set<Subject> subjects = new HashSet<Subject>();
        subjects.add(subject);
        tutor.setSubjects(subjects);
        tutorRepository.save(tutor);
        mark.setTutor(tutor);
        markRepository.save(mark);
    }
    @Test
    public void testFetchDataForMark() {
        setUp();

        Iterable<Mark> marks = markRepository.findAll();

        Mark mark = new Mark();
        Student student = new Student();
        Tutor tutor = new Tutor();
        Subject subject = new Subject();

        for (Mark m : marks) {
            Assert.assertNotNull(m.getId());
            student = m.getStudent();
            tutor = m.getTutor();
            subject = m.getSubject();
            mark = m;
            System.out.println(m);
        }
        // testing findByStudent
        Mark markByStudent = markRepository.findByStudent(student);
        Assert.assertEquals(mark.getId(), markByStudent.getId());
        System.out.println("*** ONE MARK = findByStudent ***");
        System.out.println(markByStudent);
        // testing findByTutor
        Iterable<Mark> markByTutor = markRepository.findByTutor(tutor);
        boolean isMarkExist = lookingForMark(markByTutor, mark);
        Assert.assertTrue(isMarkExist);
        System.out.println("*** MANY MARKS = findByTutor ***");
        this.showMarks(markByTutor);
        // testing findBySubject
        Iterable<Mark> markBySubject = markRepository.findBySubject(subject);
        boolean isMarkExist2 = lookingForMark(markBySubject, mark);
        Assert.assertTrue(isMarkExist2);
        System.out.println("*** MANY MARKS = findBySubject ***");
        this.showMarks(markBySubject);
    }
    @Test
    public void testFetchDataForStudent(){
        Iterable<Student> students = studentRepository.findAll();
        for(Student std : students){
            Assert.assertNotNull(std.getIndexNumber());
            System.out.println(std);
        }

    }
    @Test
    public void testFetchDataForSubject(){
        Iterable<Subject> subjects = subjectRepository.findAll();
        for(Subject sub : subjects){
            Assert.assertNotNull(sub.getId());
            System.out.println(sub);
        }

    }
    @Test
    public void testFetchDataForTutor(){
        Iterable<Tutor> tutors = tutorRepository.findAll();
        for(Tutor t : tutors){
            Assert.assertNotNull(t.getId());
            System.out.println(t);
        }
    }
    public boolean lookingForMark(Iterable<Mark> marks, Mark mark){
        for(Mark m : marks) {
            if(m.getId().equals(mark.getId())){
                return true;
            }
        }
        return false;
    }
    public void showMarks(Iterable<Mark> marks){
        for(Mark o : marks){
            System.out.println(o.toString());
        }
    }
}
