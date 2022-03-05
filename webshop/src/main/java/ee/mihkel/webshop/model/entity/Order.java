package ee.mihkel.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@SequenceGenerator(name="seq", initialValue=120000)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="seq")
    private Long id;
    private Date timeStamp;
    private double sum;
    private boolean paid;
    @OneToOne
    private Person person;

    @ManyToMany
    private List<Product> orderProducts;
}
