package deanoffice.entities;

import java.sql.Date;

public class Mark {
    // primary key
    private int id;
    // normal fields
    private Double value;
    private Date date;
    // foreign keys
    private Student student;
    private Subject subject;
    private Tutor tutor;

    public Mark(Double value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "value=" + value +
                ", date=" + date +
                ", student=" + student +
                ", subject=" + subject +
                ", tutor=" + tutor +
                '}';
    }
}
