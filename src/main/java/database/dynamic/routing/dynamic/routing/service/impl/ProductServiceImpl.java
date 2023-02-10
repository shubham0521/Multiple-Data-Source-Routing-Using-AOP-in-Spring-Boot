package database.dynamic.routing.dynamic.routing.service.impl;

import java.util.ArrayList;
import java.util.List;

import database.dynamic.routing.dynamic.routing.annotations.TransactionType;
import database.dynamic.routing.dynamic.routing.dao.CreateProductModel;
import database.dynamic.routing.dynamic.routing.model.Product;
import database.dynamic.routing.dynamic.routing.repository.ProductRepository;
import database.dynamic.routing.dynamic.routing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shubhamkumar
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Product create(CreateProductModel model) throws InterruptedException {
    Product productToBeUpdated = Product.builder()
      .name(model.getName())
      .build();
    return productRepository.save(productToBeUpdated);
  }

  @Override
  @TransactionType(readOnly = true)
  public List<Product> getAll() {
    List<Product> productList = new ArrayList<>();
    productRepository.findAll().forEach(productList::add);
    return productList;
  }
}
