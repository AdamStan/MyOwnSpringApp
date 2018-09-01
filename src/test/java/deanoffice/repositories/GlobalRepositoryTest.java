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
        Mark mark2 = new Mark(4.0);
        mark2.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        Mark mark3 = new Mark(4.5);
        mark3.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        Mark mark4 = new Mark(5.0);
        mark4.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        Faculty faculty = new Faculty("WEEIA", "Wydzial Elektroniki, Elektrotechniki Informatyki i Automatyki");
        Faculty faculty2 = new Faculty("WBINOŻ", "Wydzial Biotechnologii i Nauk o Żywności");
        Faculty faculty3 = new Faculty("WFTIMS", "Wydzial Fyzyki Technicznej i Informatyki Stosowanej");
        facultyRepository.save(faculty);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);

        Subject subject = new Subject("Podstawy Programowania 1", faculty);
        Subject subject2 = new Subject("Analiza Matematyczna", faculty);
        Subject subject3 = new Subject("Fizyka", faculty);
        mark.setSubject(subject);
        mark2.setSubject(subject2);
        mark3.setSubject(subject3);
        mark4.setSubject(subject);
        subjectRepository.save(subject);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);

        Student student = new Student("Adam",
                "Jestem",
                "Tomaszow Mazowiecki",
                "Wesola",
                "38",
                "");
        Student student2 = new Student("Anna",
                "Kowalska",
                "Piotrkow Trybunalski",
                "Osiedle Mickiewicza",
                "12",
                "7");
        student.setUsername("student1");
        student2.setUsername("student2");

        Set<Subject> subjects = new HashSet<Subject>();
        subjects.add(subject);
        subjects.add(subject2);
        student.setSubjects(subjects);
        student.setFaculty(faculty);

        Set<Subject> subjects2 = new HashSet<Subject>();
        subjects2.add(subject3);
        subjects2.add(subject);
        student2.setSubjects(subjects2);
        student2.setFaculty(faculty);

        studentRepository.save(student);
        studentRepository.save(student2);

        mark.setStudent(student);
        mark2.setStudent(student);
        mark3.setStudent(student2);
        mark4.setStudent(student2);

        Tutor tutor = new Tutor();
        tutor.setName("Jacek"); tutor.setSurname("Placek");
        tutor.setFaculty(faculty);
        Set<Subject> subjectsForTutor = new HashSet<Subject>();
        subjectsForTutor.add(subject);
        tutor.setSubjects(subjectsForTutor);
        tutor.setUsername("tutor0");
        tutorRepository.save(tutor);

        Tutor tutor2 = new Tutor();
        tutor2.setName("Jaroslawa"); tutor2.setSurname("Bezsława");
        tutor2.setFaculty(faculty);
        Set<Subject> subjectsForTutor2 = new HashSet<Subject>();
        subjectsForTutor2.add(subject2);
        subjectsForTutor2.add(subject3);
        tutor2.setSubjects(subjectsForTutor2);
        tutor2.setUsername("tutor1");
        tutorRepository.save(tutor2);

        mark.setTutor(tutor);
        mark2.setTutor(tutor2);
        mark3.setTutor(tutor2);
        mark4.setTutor(tutor);

        markRepository.save(mark);
        markRepository.save(mark2);
        markRepository.save(mark3);
        markRepository.save(mark4);
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
        System.out.println("Student: " + student);
        Iterable<Mark> markByStudent = markRepository.findByStudent(student);
        for(Mark m : markByStudent){
            Assert.assertEquals(m.getStudent().getIndexNumber(), student.getIndexNumber());
        }
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
        // findByIndexNumber
        Integer i = 1;
        Student studentByIndexNumber = studentRepository.findByIndexNumber(i);
        Assert.assertNotNull(studentByIndexNumber);
        System.out.println("*" + studentByIndexNumber);
        // findBySurname
        String surname = "Jestem";
        Iterable<Student> studentsBySurname = studentRepository.findBySurname(surname);
        for(Student s : studentsBySurname){
            Assert.assertEquals(s.getSurname(), surname);
            System.out.println("**" + s);
        }

        // findByUsername
        String username = "student1";
        Student studentByUsername = studentRepository.findByUsername(username);
        System.out.println("***" + studentByUsername);
        Assert.assertEquals(studentByUsername.getUsername(), username);
    }
    @Test
    public void testFetchDataForSubject(){
        Iterable<Subject> subjects = subjectRepository.findAll();
        for(Subject sub : subjects){
            Assert.assertNotNull(sub.getId());
            System.out.println(sub);
        }
        // findByName
        String subjectName = "Podstawy Programowania 1";
        Subject sub1 = subjectRepository.findByName(subjectName);
        Assert.assertEquals(sub1.getName(), subjectName);
        System.out.println("*" + sub1);
        // findByFaculty
        Faculty fac = facultyRepository.findByName("WEEIA");
        Iterable<Subject> subjectsByFaculty = subjectRepository.findByFaculty(fac);
        for(Subject sub : subjectsByFaculty){
            Assert.assertEquals(sub.getFaculty().getName(), fac.getName());
            System.out.println("**" + sub);
        }
    }
    @Test
    public void testFetchDataForTutor(){
        Iterable<Tutor> tutors = tutorRepository.findAll();
        for(Tutor t : tutors){
            Assert.assertNotNull(t.getId());
            System.out.println(t);
        }
        // findBySurname
        String surname = "Placek";
        Iterable<Tutor> tutorsBySurname = tutorRepository.findBySurname(surname);
        for(Tutor t : tutorsBySurname){
            Assert.assertEquals(t.getSurname(), surname);
            System.out.println("*" + t);
        }
        // findByFaculty
        Faculty fac = facultyRepository.findByName("WEEIA");
        Iterable<Tutor> tutorsByFaculty = tutorRepository.findByFaculty(fac);
        for(Tutor t : tutorsByFaculty){
            Assert.assertEquals(t.getFaculty().getName(), fac.getName());
            System.out.println("**" + t);
        }
        // findByUsername
        String username = "tutor0";
        Tutor tutorByUsername = tutorRepository.findByUsername(username);
        Assert.assertEquals(tutorByUsername.getUsername(), username);
        System.out.println("***" + tutorByUsername);
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
