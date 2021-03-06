package org.yanwen.core.repository;


import org.yanwen.core.domain.Brand;

import java.util.List;

public interface BrandDao {
    Brand save(Brand brand);
    List<Brand> getBrands();
    Brand getBy(long id);
    Brand getBrandEagerBy(long id);
    Brand update(Brand brand);
    boolean delete(Brand brand);
}
