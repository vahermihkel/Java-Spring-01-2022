package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Order;
import ee.mihkel.webshop.model.entity.Person;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    // hiljem teeme ka ResponseEntity -
    // saan anda enda kindla HttpStatuse ja vajadusel Headerid
    @GetMapping("orders/{personCode}")
    public List<Order> getOrders(@PathVariable String personCode) {
        Person person = personRepository.findById(personCode).get();
        List<Order> orders = orderRepository.getAllByPerson(person);
        return orders;
    }
}

// SELECT * FROM andmebaas;
// SELECT * FROM andmebaas WHERE isikukood = personCode JOIN Person JOIN Product;
