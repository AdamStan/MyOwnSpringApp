package deanoffice.repositories;

import deanoffice.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    Address findByCity(String city);
}
