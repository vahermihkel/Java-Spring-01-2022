package ee.mihkel.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import ee.mihkel.webshop.model.entity.Order;
import ee.mihkel.webshop.model.entity.Person;
import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component  // Bean -ks - et seda saaks Autowire-dada
            // @Component / @Service / @Repository / @Controller - @RestController on eriline
            // @Configuration - võimaldab funktsioone kirjutada mis muudavad nö core springi
@Log4j2
public class ProductCache {

    @Autowired
    ProductRepository productRepository;

    private final LoadingCache<Long, Product> productCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, Product>() {
                @Override
                public Product load(Long id) throws Exception {
                    return productRepository.findById(id).get();
                }
            });

    public Product getProduct(Long id) throws ExecutionException {
        return productCache.get(id);
    }

    // iterable on nagu List, ainult ei saa funktsioone teha
    // ei saa ükshaaval võtta, lisada, kustutada, muuta
    public List<Product> getProducts(Iterable<Long> ids) throws ExecutionException {
        return new ArrayList<>(productCache.getAll(ids).values());
    }
    // .getAll(ids) --- annab mulle Map'i, ehk [{1: Product},{7: Product}]

    // muudame ära nii GET mapping ID alusel kättesaamine
    // maksma minnes toodete kättesaamine

    // 1. küsib neid tooteid cache seest
    //    a. kui ei ole cache sees, siis võtab andmebaasist
    //    b. kui on cache sees, siis võtab sealt
}
