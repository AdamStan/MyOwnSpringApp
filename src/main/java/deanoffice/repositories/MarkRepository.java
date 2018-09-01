package deanoffice.repositories;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark, Integer> {
    Iterable<Mark> findByStudent(Student student);
    Iterable<Mark> findByTutor(Tutor tutor);
    Iterable<Mark> findBySubject(Subject subject);
}
