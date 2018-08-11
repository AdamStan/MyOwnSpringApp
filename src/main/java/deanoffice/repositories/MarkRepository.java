package deanoffice.repositories;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark, Integer> {
    Mark findByStudent(Student student);
    Mark findByTutor(Tutor tutor);
}
