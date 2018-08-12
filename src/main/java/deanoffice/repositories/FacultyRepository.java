package deanoffice.repositories;

import deanoffice.entities.Faculty;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepository extends CrudRepository<Faculty, Integer> {
    Faculty findByName(String name);
}
