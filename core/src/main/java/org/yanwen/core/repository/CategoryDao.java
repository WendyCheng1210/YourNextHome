package org.yanwen.core.repository;

import org.yanwen.core.domain.Category;

import java.util.List;

public interface CategoryDao {
    Category save(Category category);
    List<Category> getCategories();
    Category getBy(long id);
    Category getCategoryEagerBy(long id);
    Category update(Category category);
    boolean delete(Category category);
}
