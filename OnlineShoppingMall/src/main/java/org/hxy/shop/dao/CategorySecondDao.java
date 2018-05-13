package org.hxy.shop.dao;

import org.hxy.shop.model.CategorySecond;

import java.util.List;

public interface CategorySecondDao {
    public int findCount();
    public List<CategorySecond> findByPage(Integer begin,Integer limit);
    public void save(CategorySecond categorySecond);
    public CategorySecond findByCsid(Integer csid);
    public void delete(CategorySecond categorySecond);
    public void update(CategorySecond categorySecond);
    public List<CategorySecond> findAll();
}
