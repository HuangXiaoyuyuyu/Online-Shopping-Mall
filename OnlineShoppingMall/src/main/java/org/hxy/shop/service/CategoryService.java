package org.hxy.shop.service;

import org.hxy.shop.model.Category;

import java.util.List;

public interface CategoryService {
    public List findAllCategory();
    public void save(Category category);
    public Category findByCid(Integer cid);
    public void delete(Category category);
    public void update(Category category);
}
