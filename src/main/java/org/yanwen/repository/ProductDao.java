package org.yanwen.repository;

import org.yanwen.model.Order;
import org.yanwen.model.Product;

import java.util.List;

public interface ProductDao {

    Product save(Product product);
    List<Product> getProducts();
    Product getBy(long id);
    Product update(Product product);
    boolean delete(Product product);
}
