package org.hxy.shop.daoImpl;

import org.hxy.shop.dao.CategoryDao;
import org.hxy.shop.model.Category;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao{

    @Resource
    private HibernateTemplate hibernateTemplate;

    //查询所有一级分类
    @Override
    public List findAllCategory() {
        String hql = "from Category";
        List<Category> categories = (List<Category>) hibernateTemplate.find(hql);
        return categories;
    }

    //保存一级分类
    @Override
    public void save(Category category) {
        hibernateTemplate.save(category);
    }

    //根据cid查询一级分类
    @Override
    public Category findByCid(Integer cid) {
        return hibernateTemplate.get(Category.class,cid);
    }

    //删除一级分类
    @Override
    public void delete(Category category) {
        hibernateTemplate.delete(category);
    }

    //修改一级分类
    @Override
    public void update(Category category) {
        hibernateTemplate.update(category);
    }
}
