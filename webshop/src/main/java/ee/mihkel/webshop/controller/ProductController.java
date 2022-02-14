package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// sõnade otsimiseks:  ctrl + shift + f
// failide otsimiseks: shift + shift - 2x shift

// Annoteerime RestController, et oleks koguaeg olemas
// Controller peab olema koguaeg olemas päringute valmiduse osas
@RestController
@Log4j2
//@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    // ProductRepository productRepository = new ProductRepository();

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findByOrderByIdAsc();
    }
    // GET - võtmiseks
    // POST - lõppu juurde lisamiseks
    // PUT - asendamiseks terve objekti või massiivi ulatuses
    // PATCH - muutmiseks ühe objekti ühe või mitme (aga mitte kõikide)
    //              väärtuste osas
    // DELETE - kustutamiseks

    @PostMapping("products")
    public String addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return "Edukalt lisatud uus toode: " + product.getName();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

//    @DeleteMapping("products")
//    public String deleteAllProducts() {
//        productRepository.flush();
//        return "Edukalt kustutatud kõik tooted" ;
//    }

    @PutMapping("products/{id}")
    public String editProduct(@PathVariable Long id, @RequestBody Product product) {
        productRepository.save(product);
        return "Edukalt muudetud toode id-ga: " + id;
    }

    // Optional<Product> -- null on öeldud et on ka korrektne

    // Product product = null;
    //        if (productRepository.findById(id).isPresent()) {
    //            product = productRepository.findById(id).get();
    //        }
    @GetMapping("products/{id}")
    public Product viewProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        System.out.println(product);
        return product;
    }

    @PutMapping("products")
    public String addAllProducts(@RequestBody List<Product> products) {
        productRepository.saveAll(products);
        return "Edukalt kõik tooted lisatud andmebaasi";
    }

    // Patch on ühe või mitme omaduse muutmine
    @PatchMapping("increase-quantity")
    public String increaseProductQuantity(@RequestBody Product product) {
        // kas läheb muutma või lisama, käib ID alusel
        // kui .save sees antakse selline objekt, kelle ID on juba olemas
        //              siis selle IDga kõik andmed muudetakse
        // kui ID-d kaasa ei anta VÕI on selline ID mida ei ole olemas
        //              siis lisatakse
        if (productRepository.findById(product.getId()).isPresent()) {
            int productQuantity = product.getQuantity();
            product.setQuantity(++productQuantity);
            productRepository.save(product);
            return "Edukalt toote kogus suurendatud";
        } else {
            return "Toodet mille kogust taheti muuta ei leitud";
        }

    }

    @PatchMapping("decrease-quantity")
    public String decreaseProductQuantity(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isPresent() &&
                product.getQuantity() > 0) {
            int productQuantity = product.getQuantity();
            product.setQuantity(--productQuantity);
            productRepository.save(product);
            return "Edukalt toote kogus vähendatud";
        } else if (product.getQuantity() < 1) {
           return "Tootel mille kogust taheti vähendada on kogus liiga väike";
        } else {
            return "Toodet mille kogust taheti muuta ei leitud";
        }

    }
}
