package ee.mihkel.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Annoteerime RestController, et oleks koguaeg olemas
// Controller peab olema koguaeg olemas päringute valmiduse osas
@RestController
//@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    // ProductRepository productRepository = new ProductRepository();

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findAll();
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
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Edukalt kustutatud toode id-ga:" + id;
    }

    @DeleteMapping("products")
    public String deleteAllProducts() {
        productRepository.flush();
        return "Edukalt kustutatud kõik tooted" ;
    }

    @PutMapping("products/{id}")
    public String editProduct(@PathVariable Long id, @RequestBody Product product) {
        productRepository.save(product);
        return "Edukalt muudetud toode id-ga: " + id;
    }
}

// E 10:00-13:00
// R 14:00-17:00
