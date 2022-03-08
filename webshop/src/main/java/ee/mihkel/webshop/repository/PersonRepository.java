package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {

    Person getPersonByEmail(String email);
}
