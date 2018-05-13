package org.hxy.shop.daoImpl;

import org.hxy.shop.Utils.PageHibernateCallback;
import org.hxy.shop.dao.CategorySecondDao;
import org.hxy.shop.model.CategorySecond;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("categorySecondDao")
public class CategorySecondDaoImpl implements CategorySecondDao{

    @Resource
    private HibernateTemplate hibernateTemplate;

    //统计二级分类个数的方法
    @Override
    public int findCount() {
        String hql = "select count(*) from CategorySecond";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }else {
            return 0;
        }
    }

    //分页查询二级分类的方法
    @Override
    public List<CategorySecond> findByPage(Integer begin, Integer limit) {
        String hql = "from CategorySecond order by csid desc";
        List<CategorySecond> list = (List<CategorySecond>) hibernateTemplate.execute(new PageHibernateCallback<CategorySecond>(hql, null, begin, limit));
        if (list != null && list.size() > 0) {
            return list;
        }else {
            return null;
        }
    }

    //二级分类的保存
    @Override
    public void save(CategorySecond categorySecond) {
        hibernateTemplate.save(categorySecond);
    }

    //根据二级分类id查询二级分类
    @Override
    public CategorySecond findByCsid(Integer csid) {
        return hibernateTemplate.get(CategorySecond.class,csid);
    }

    //删除二级分类
    @Override
    public void delete(CategorySecond categorySecond) {
        hibernateTemplate.delete(categorySecond);
    }

    //修改二级分类
    @Override
    public void update(CategorySecond categorySecond) {
        hibernateTemplate.update(categorySecond);
    }

    //查询所有二级分类
    @Override
    public List<CategorySecond> findAll() {
        String hql = "from CategorySecond";
        return (List<CategorySecond>) hibernateTemplate.find(hql);
    }
}
