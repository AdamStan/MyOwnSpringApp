package deanoffice.entities;

import java.sql.Date;
import java.util.Set;

public class Tutor {
    // primary key
    private int id;
    // normal fields
    private String name;
    private String surname;
    private String username;
    private Date whenStarted;
    private Date whenFinnished;
    // foreign keys
    private Address address;
    private Faculty faculty;
    //
    private Set<Mark> marks;
    private Set<Subject> subjects;

    public Tutor(String name, String surname, Date whenStarted, Address address, Faculty faculty) {
        this.name = name;
        this.surname = surname;
        this.whenStarted = whenStarted;
        this.address = address;
        this.faculty = faculty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(Date whenStarted) {
        this.whenStarted = whenStarted;
    }

    public Date getWhenFinnished() {
        return whenFinnished;
    }

    public void setWhenFinnished(Date whenFinnished) {
        this.whenFinnished = whenFinnished;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
