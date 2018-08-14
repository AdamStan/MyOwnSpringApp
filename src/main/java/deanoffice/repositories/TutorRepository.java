package deanoffice.repositories;

import deanoffice.entities.Faculty;
import deanoffice.entities.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorRepository extends CrudRepository<Tutor, Integer> {
    Iterable<Tutor> findBySurname(String surname);
    Iterable<Tutor> findByFaculty(Faculty faculty);
    Tutor findByUsername(String username);
}
