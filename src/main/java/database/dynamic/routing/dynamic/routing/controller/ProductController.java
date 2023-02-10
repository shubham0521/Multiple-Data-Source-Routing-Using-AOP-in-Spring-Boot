package database.dynamic.routing.dynamic.routing.controller;

import java.util.List;

import database.dynamic.routing.dynamic.routing.dao.CreateProductModel;
import database.dynamic.routing.dynamic.routing.model.Product;
import database.dynamic.routing.dynamic.routing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shubhamkumar
 */

@RestController
@RequestMapping("/api/v1")
public class ProductController {

  @Autowired
  private ProductService service;

  @PostMapping("/addProducts")
  public Product addBooks(@RequestBody CreateProductModel createProductModel) throws InterruptedException {
    return service.create(createProductModel);
  }

  @GetMapping("/getAllProducts")
  public List<Product> getAllProducts() {
    return service.getAll();
  }
}
