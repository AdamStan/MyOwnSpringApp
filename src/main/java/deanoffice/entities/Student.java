package deanoffice.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int indexNumber;
    // normal fields
    private String name;
    private String surname;
    private String username;
    private Date whenStarted;
    private Date whenFinnished;
    private String city;
    private String street;
    private String numberOfBuilding;
    private String numberOfFlat;
    // foreign keys
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id", nullable = true)
    private Faculty faculty;
    // one student to many marks
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private Set<Mark> marks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "studentToSubject",
        joinColumns = { @JoinColumn(name = "student_id", nullable = true, updatable = true) },
        inverseJoinColumns = { @JoinColumn(name = "subject_id", nullable = true, updatable = true) }
    )
    private Set<Subject> subjects;

    public Student(){

    }

    public Student(String name, String surname, String city, String street, String numberOfBuilding, String numberOfFlat) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.street = street;
        this.numberOfBuilding = numberOfBuilding;
        this.numberOfFlat = numberOfFlat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumberOfBuilding() {
        return numberOfBuilding;
    }

    public void setNumberOfBuilding(String numberOfBuilding) {
        this.numberOfBuilding = numberOfBuilding;
    }

    public String getNumberOfFlat() {
        return numberOfFlat;
    }

    public void setNumberOfFlat(String numberOfFlat) {
        this.numberOfFlat = numberOfFlat;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
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

    @Override
    public String toString() {
        return indexNumber + ", " + name + ", " + surname;
    }
}
