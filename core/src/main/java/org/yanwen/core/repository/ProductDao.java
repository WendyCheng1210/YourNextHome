package org.yanwen.core.repository;

import org.yanwen.core.domain.Product;

import java.util.List;

public interface ProductDao {

    Product save(Product product);
    List<Product> getProducts();
    Product getBy(long id);
    Product update(Product product);
    boolean delete(Product product);
}
