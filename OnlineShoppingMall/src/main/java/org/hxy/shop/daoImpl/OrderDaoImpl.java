package org.hxy.shop.daoImpl;

import org.hxy.shop.dao.OrderDao;
import org.hxy.shop.model.Order;
import org.hxy.shop.Utils.PageHibernateCallback;
import org.hxy.shop.model.Orderitem;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    @Override
    public void save(Order order) {
        hibernateTemplate.save(order);
    }

    //订单个数统计
    @Override
    public int findByUid(int uid) {
        String hql = "select count(*) from Order o where o.user.uid = ?";
        List<Long> longs = (List<Long>) hibernateTemplate.find(hql,uid);
        if (longs != null && longs.size() > 0){
            return longs.get(0).intValue();
        }
        return 0;
    }

    //我的订单的查询
    @Override
    public List findByPageUid(int uid, int begin, int limit) {
     //   String hql = "select o from Order o  join o.user u where u.id = ? order by ordertime desc";
        String hql = "from Order o where o.user.uid = ? order by ordertime desc";
        List<Order> orders = (List<Order>) hibernateTemplate.execute(new PageHibernateCallback<Order>(hql,new Object[]{uid},begin,limit));
        if (orders != null && orders.size() > 0){
            return orders;
        }
        return null;
    }

    @Override
    public Order findByOid(int oid) {
        return (Order) hibernateTemplate.get(Order.class,oid);
    }

    @Override
    public void update(Order order) {
        hibernateTemplate.update(order);
    }

    //统计订单个数
    @Override
    public int findByCount() {
        String hql = "select count(*) from Order";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    //带分页查询
    @Override
    public List<Order> findByPage(int begin, int limit) {
        String hql = "from Order order by ordertime desc";
        List<Order> list = (List<Order>) hibernateTemplate.execute(new PageHibernateCallback<Order>(hql, null, begin, limit));
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    //根据订单id查询订单项
    @Override
    public List<Orderitem> findOrderItem(Integer oid) {
        String hql = "from Orderitem oi where oi.order.oid = ?";
        List<Orderitem> list = (List<Orderitem>) hibernateTemplate.find(hql, oid);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
}
