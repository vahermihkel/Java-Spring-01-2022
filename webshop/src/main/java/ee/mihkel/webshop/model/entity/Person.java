package ee.mihkel.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    private String personCode;
    private String firstName; // NotNull ja NotBlank
    private String lastName; // NotNull ja NotBlank
    @Column(unique = true)
    private String email;
    private String phone;
    private String password;

    // Repository
    // Controller, kus saab lisada, muuta, kustutada, kõiki võtta
}
