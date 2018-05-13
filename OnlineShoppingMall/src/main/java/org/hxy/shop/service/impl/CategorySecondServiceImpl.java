package org.hxy.shop.service.impl;

import org.hxy.shop.dao.CategorySecondDao;
import org.hxy.shop.model.CategorySecond;
import org.hxy.shop.service.CategorySecondService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categorySecondService")
public class CategorySecondServiceImpl implements CategorySecondService {

    @Resource
    private CategorySecondDao categorySecondDao;

    @Override
    public int findCount() {
        return categorySecondDao.findCount();
    }

    @Override
    public List<CategorySecond> findByPage(Integer begin, Integer limit) {
        return categorySecondDao.findByPage(begin,limit);
    }

    @Override
    public void save(CategorySecond categorySecond) {
        categorySecondDao.save(categorySecond);
    }

    @Override
    public CategorySecond findByCsid(Integer csid) {
        return categorySecondDao.findByCsid(csid);
    }

    @Override
    public void delete(CategorySecond categorySecond) {
        categorySecondDao.delete(categorySecond);
    }

    @Override
    public void update(CategorySecond categorySecond) {
        categorySecondDao.update(categorySecond);
    }

    @Override
    public List<CategorySecond> findAll() {
        return categorySecondDao.findAll();
    }
}
