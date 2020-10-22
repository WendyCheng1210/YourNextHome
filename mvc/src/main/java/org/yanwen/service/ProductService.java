package org.yanwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.model.Product;
import org.yanwen.repository.ProductDao;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product save(Product product){return productDao.save(product);}
    public List<Product> getProducts(){return productDao.getProducts();}
    public Product getBy(long id){return productDao.getBy(id);}
    public Product update(Product product){return productDao.update(product);}
    public boolean delete(Product product){return productDao.delete(product);}

}
