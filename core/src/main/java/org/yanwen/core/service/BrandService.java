package org.yanwen.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.core.domain.Brand;
import org.yanwen.core.repository.BrandDao;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    public Brand save(Brand brand){return brandDao.save(brand);}
    public List<Brand> getBrands(){
        return brandDao.getBrands();
    }
    public Brand update(Brand brand){
        return brandDao.update(brand);
    }
    public Brand getBy(long Id){
        return brandDao.getBy(Id);
    }
    public boolean delete(Brand brand){return brandDao.delete(brand);}
}
