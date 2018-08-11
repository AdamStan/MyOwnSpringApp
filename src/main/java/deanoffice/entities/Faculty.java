package deanoffice.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // normal fields
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "faculty")
    private Set<Student> students;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "faculty")
    private Set<Tutor> tutors;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "faculty")
    private Set<Subject> subjects;

    public Faculty(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(Set<Tutor> tutors) {
        this.tutors = tutors;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
