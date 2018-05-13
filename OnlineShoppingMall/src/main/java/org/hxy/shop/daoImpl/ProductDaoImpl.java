package org.hxy.shop.daoImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hxy.shop.dao.ProductDao;
import org.hxy.shop.model.Product;
import org.hxy.shop.Utils.PageHibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    @Override
    public List findAllHot() {
        //使用离线条件查询
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        //查询热门商品 is_hot = 1
        criteria.add(Restrictions.eq("is_hot",1));
        //按照日期倒序排序输出
        criteria.addOrder(Order.desc("pdate"));
        List<Product> products = (List<Product>) hibernateTemplate.findByCriteria(criteria,0,10);
        return products;
    }

    @Override
    public List findAllNew() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        criteria.addOrder(Order.desc("pdate"));
        List<Product> products = (List<Product>) hibernateTemplate.findByCriteria(criteria,0,10);
        return products;
    }

    //根据商品id查询商品
    @Override
    public Product findByPid(int pid) {
        return (Product) hibernateTemplate.get(Product.class,pid);
    }

    //通过一级目录id查询商品数量
    @Override
    public int findCountCid(int cid) {
        String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, cid);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    //通过二级目录id查询商品数量
    @Override
    public int findCountCsid(int csid) {
        String hql = "select count(*) from Product p where p.categorySecond.csid = ?";
        List<Long> longs = (List<Long>) hibernateTemplate.find(hql,csid);
        if (longs != null && longs.size() > 0) {
            return longs.get(0).intValue();
        }
        return 0;
    }

    //根据一级分类id查询商品集合
    @Override
    public List findByPageCid(int cid, int begin, int limit) {
        //select * from product p,categorysecond cs,category c WHERE c.cid=cs.cid and cs.csid = p.csid and c.cid = 1;
        //select * from Product p,CategorySecond cs,Category c where c.cid=cs.category.cid and cs.csid = p.categorySecond.csid and c.cid = ?
        String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
        List<Product> list = (List<Product>) hibernateTemplate.execute(new PageHibernateCallback<Product>(hql,new Object[]{cid},begin,limit));
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    //根据二级分类id查询商品集合
    @Override
    public List findByPageCsid(int csid, int begin, int limit) {
        String hql = "select p from Product p join p.categorySecond cs where cs.csid = ?";
        List<Product> list = (List<Product>) hibernateTemplate.execute(new PageHibernateCallback<Product>(hql,new Object[]{csid},begin,limit));
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    //统计商品个数
    @Override
    public int findCount() {
        String hql = "select count(*) from Product";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    //带有分页查询商品
    @Override
    public List<Product> findByPage(int begin, int limit) {
        String hql = "from Product order by pdate desc";
        List<Product> products = (List<Product>) hibernateTemplate.execute(new PageHibernateCallback<Product>(hql, null, begin, limit));
        if (products != null && products.size() > 0) {
            return products;
        }
        return null;
    }

    //保存商品
    @Override
    public void save(Product product) {
        hibernateTemplate.save(product);
    }

    //删除商品
    @Override
    public void delete(Product product) {
        hibernateTemplate.delete(product);
    }

    //更新商品
    @Override
    public void update(Product product) {
        hibernateTemplate.update(product);
    }


}
