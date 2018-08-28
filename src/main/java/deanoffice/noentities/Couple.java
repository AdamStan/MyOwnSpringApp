package deanoffice.noentities;

import deanoffice.entities.Student;
import deanoffice.entities.Subject;

public class Couple {
    Student student;
    Subject subject;

    public Couple(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
