package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriesController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok()
                .body(categoryRepository.findAll());
    }

    @PostMapping("categories")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Edukalt lisatud uus kategooria: " + category.getName());
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok()
                .body(categoryRepository.findAll());
    }


    @PutMapping("categories/{id}")
    public ResponseEntity<String> editCategory(@PathVariable Long id, @RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok()
                .body("Edukalt muudetud kategooria id-ga: " + id);
    }
}

