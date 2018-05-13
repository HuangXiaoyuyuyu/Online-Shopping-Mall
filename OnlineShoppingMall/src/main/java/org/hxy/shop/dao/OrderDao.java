package org.hxy.shop.dao;

import org.hxy.shop.model.Order;
import org.hxy.shop.model.Orderitem;

import java.util.List;

public interface OrderDao {
    public void save(Order order);
    public int findByUid(int uid);
    public List findByPageUid(int uid, int begin, int limit);
    public Order findByOid(int oid);
    public void update(Order order);
    public int findByCount();
    public List<Order> findByPage(int begin,int limit);
    public List<Orderitem> findOrderItem(Integer oid);
}
