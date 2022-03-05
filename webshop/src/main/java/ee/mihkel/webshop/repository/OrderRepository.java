package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.entity.Order;
import ee.mihkel.webshop.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByPerson(Person person);
}
