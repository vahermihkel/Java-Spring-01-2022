package ee.mihkel.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Annotation
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotNull
    private int quantity;

    @NotNull
    @NotBlank
    private String imgSrc;

    @NotNull
    private boolean active;

    @NotNull
    @NotBlank
    private String category;

    @NotNull
    @NotBlank // mitte "null", mitte "", mitte " "
    private String description;

    @NotNull // mitte "null"
    //@Column(unique = true) -- ainult siis kui tabelit ei ole tehtud
    private long barcode;
}

// Product 1,2,3,4
// User 1
