package deanoffice.repositories;

import deanoffice.entities.Faculty;
import deanoffice.entities.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    Subject findByName(String name);
    Iterable<Subject> findByFaculty(Faculty faculty);
}
