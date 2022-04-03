package ee.mihkel.webshop;

import ee.mihkel.webshop.model.entity.Category;
import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.repository.CategoryRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebshopApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void assertThatAddedProductExistsInRepository_whenAdded() {
        Category category = categoryRepository.findById(1L).get();
        Product testProduct = new Product(
                99L,
                "Toode2",
                1.0,
                1, "pildi_url",
                true,
                category,
                "desc",
                100);
        productRepository.save(testProduct);
        Product product = productRepository.findById(99L).get();
        Assertions.assertEquals(
                "Toode2",
                product.getName());
    }

}
