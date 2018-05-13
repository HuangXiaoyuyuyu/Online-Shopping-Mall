package org.hxy.shop.service.impl;

import org.hxy.shop.dao.CategoryDao;
import org.hxy.shop.model.Category;
import org.hxy.shop.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Override
    public List findAllCategory() {
        return categoryDao.findAllCategory();
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public Category findByCid(Integer cid) {
        return categoryDao.findByCid(cid);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }
}
