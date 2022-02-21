package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok()
//              .headers(responseHeaders)
                .body(productRepository.findByOrderByIdAsc());


//        new ResponseEntity<>(productRepository.findByOrderByIdAsc(),
//                responseHeaders,
//                HttpStatus.OK);
    }
    // GET - võtmiseks
    // POST - lõppu juurde lisamiseks
    // PUT - asendamiseks terve objekti või massiivi ulatuses
    // PATCH - muutmiseks ühe objekti ühe või mitme (aga mitte kõikide)
    //              väärtuste osas
    // DELETE - kustutamiseks

    @PostMapping("products")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Edukalt lisatud uus toode: " + product.getName());


//        new ResponseEntity<>("Edukalt lisatud uus toode: " + product.getName(),
//  //              responseHeaders,
//                HttpStatus.CREATED);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok()
                .body(productRepository.findByOrderByIdAsc());
    }

//    @DeleteMapping("products")
//    public String deleteAllProducts() {
//        productRepository.flush();
//        return "Edukalt kustutatud kõik tooted" ;
//    }

    @PutMapping("products/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok()
                .body("Edukalt muudetud toode id-ga: " + id);
    }

    // Optional<Product> -- null on öeldud et on ka korrektne

    // Product product = null;
    //        if (productRepository.findById(id).isPresent()) {
    //            product = productRepository.findById(id).get();
    //        }
    @GetMapping("products/{id}")
    public ResponseEntity<Product> viewProduct(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(productRepository.findById(id).get());
    }

    @PutMapping("products")
    public ResponseEntity<String> addAllProducts(@RequestBody List<Product> products) {
        productRepository.saveAll(products);
        return ResponseEntity.ok()
                .body("Edukalt kõik tooted lisatud andmebaasi");
    }

    // Patch on ühe või mitme omaduse muutmine
    @PatchMapping("increase-quantity")
    public ResponseEntity<String> increaseProductQuantity(@RequestBody Product product) {
        // kas läheb muutma või lisama, käib ID alusel
        // kui .save sees antakse selline objekt, kelle ID on juba olemas
        //              siis selle IDga kõik andmed muudetakse
        // kui ID-d kaasa ei anta VÕI on selline ID mida ei ole olemas
        //              siis lisatakse
        if (productRepository.findById(product.getId()).isPresent()) {
            int productQuantity = product.getQuantity();
            product.setQuantity(++productQuantity);
            productRepository.save(product);
            return ResponseEntity.ok()
                    .body("Edukalt toote kogus suurendatud");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Toodet mille kogust taheti muuta ei leitud");
        }
    }

    @PatchMapping("decrease-quantity")
    public ResponseEntity<String> decreaseProductQuantity(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isPresent() &&
                product.getQuantity() > 0) {
            int productQuantity = product.getQuantity();
            product.setQuantity(--productQuantity);
            productRepository.save(product);
            return ResponseEntity.ok()
                    .body("Edukalt toote kogus vähendatud");
        } else if (product.getQuantity() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tootel mille kogust taheti vähendada on kogus liiga väike");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Toodet mille kogust taheti muuta ei leitud");
        }

    }
}
