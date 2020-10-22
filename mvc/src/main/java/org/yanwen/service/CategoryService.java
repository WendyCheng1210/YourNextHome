package org.yanwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.model.Category;
import org.yanwen.model.Order;
import org.yanwen.repository.CategoryDao;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public Category save(Category category){return categoryDao.save(category);}
    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }
    public Category update(Category category){
        return categoryDao.update(category);
    }
    public Category getBy(long Id){
        return categoryDao.getBy(Id);
    }
    public boolean delete(Category category){return categoryDao.delete(category);}
}
