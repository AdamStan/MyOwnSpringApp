package deanoffice.repositories;

import deanoffice.entities.Faculty;
import deanoffice.entities.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorRepository extends CrudRepository<Tutor, Integer> {
    Tutor findBySurname(String surname);
    Tutor findByFaculty(Faculty faculty);
    Tutor findByUsername(String username);
}
