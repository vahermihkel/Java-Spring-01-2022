package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.entity.Category;
import ee.mihkel.webshop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
