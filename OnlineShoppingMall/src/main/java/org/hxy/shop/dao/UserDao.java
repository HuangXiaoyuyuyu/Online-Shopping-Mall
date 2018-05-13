package org.hxy.shop.dao;

import org.hxy.shop.model.User;

import java.util.List;

public interface UserDao {
    public User findByName(String username);//按照名称查询是否有该用户
    public User findByCode(String code);
    public void save(User user);
    public void update(User user);
    public User login(User user);
    public int findCount();
    public List<User> findByPage(int begin,int limit);
    public User findByUid(Integer uid);
    public void delete(User exitUser);
}
