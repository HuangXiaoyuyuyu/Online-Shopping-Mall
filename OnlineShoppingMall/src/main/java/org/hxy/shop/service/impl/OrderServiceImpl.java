package org.hxy.shop.service.impl;

import org.hxy.shop.dao.OrderDao;
import org.hxy.shop.model.Order;
import org.hxy.shop.model.Orderitem;
import org.hxy.shop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public int findByUid(int uid) {
        return orderDao.findByUid(uid);

    }

    @Override
    public List findByPageUid(int uid, int begin, int limit) {
        return orderDao.findByPageUid(uid, begin, limit);
    }

    @Override
    public Order findByOid(int oid) {
        return orderDao.findByOid(oid);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public int findByCount() {
        return orderDao.findByCount();
    }

    @Override
    public List<Order> findByPage(int begin, int limit) {
        return orderDao.findByPage(begin, limit);
    }

    @Override
    public List<Orderitem> findOrderItem(Integer oid) {
        return orderDao.findOrderItem(oid);
    }
}
