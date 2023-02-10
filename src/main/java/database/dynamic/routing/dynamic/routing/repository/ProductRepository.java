package database.dynamic.routing.dynamic.routing.repository;

import database.dynamic.routing.dynamic.routing.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * @author shubhamkumar
 */


public interface ProductRepository extends CrudRepository<Product, Integer> {
}
