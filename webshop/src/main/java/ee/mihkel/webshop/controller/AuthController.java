package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Person;
import ee.mihkel.webshop.model.request.input.LoginData;
import ee.mihkel.webshop.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping
    public ResponseEntity<Boolean> login(@RequestBody LoginData loginData) {
        boolean isSuccessful = false;
        if (loginData.getEmail() != null && loginData.getPassword() != null) {
            Person person = personRepository.getPersonByEmail(loginData.getEmail());
            if (person != null) {
                log.debug(loginData.getPassword());
                log.debug(person.getPassword());
            }
        }

        return ResponseEntity.ok().body(isSuccessful);
    }
}
