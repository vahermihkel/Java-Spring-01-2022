package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Person;
import ee.mihkel.webshop.model.request.input.LoginData;
import ee.mihkel.webshop.model.request.output.AuthData;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.TokeniEhitaja;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Scanner;

@RestController
@Log4j2
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokeniEhitaja tokeniEhitaja;

    @PostMapping("login")
    public ResponseEntity<AuthData> login(@RequestBody LoginData loginData) {
//        boolean isSuccessful = false;
        AuthData authData = null;
        if (loginData.getEmail() != null && loginData.getPassword() != null) {
            Person person = personRepository.getPersonByEmail(loginData.getEmail());
            if (person != null) {
//                log.info(encoder.encode(loginData.getPassword()));
//                log.info(person.getPassword());
                // error, info, debug
                // JwtBuilder
                // TokeniEhitaja
                if (encoder.matches(loginData.getPassword(), person.getPassword())) {
                    authData = tokeniEhitaja.createJwtAuthtoken(person.getEmail());
                    log.info(authData.getToken());
                    log.info(authData.getExpiration());
                }
            }
        }
        if (authData == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok().body(authData);
        }
    }

    @PostMapping("signup")
    public String signup(@RequestBody Person person) {
        String hashedPassword = encoder.encode(person.getPassword()); // võetakse parool kasutajlt ja hashitakse
        person.setPassword(hashedPassword); // pannakse see hashitud parool asemele
        personRepository.save(person);
        return "New person added> " + person.getFirstName() + " " + person.getLastName();
    }
}
