package database.dynamic.routing.dynamic.routing.service;

import java.util.List;

import database.dynamic.routing.dynamic.routing.dao.CreateProductModel;
import database.dynamic.routing.dynamic.routing.model.Product;

/**
 * @author shubhamkumar
 */

public interface ProductService {

  Product create(CreateProductModel model) throws InterruptedException;

  List<Product> getAll();
}
