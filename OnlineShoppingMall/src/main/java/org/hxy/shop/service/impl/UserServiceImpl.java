package org.hxy.shop.service.impl;

import org.hxy.shop.dao.UserDao;
import org.hxy.shop.model.User;
import org.hxy.shop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    //根据用户名查找用户
    @Override
    public User findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public User findByCode(String code) {
        return userDao.findByCode(code);
    }

    //用户注册
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public int findCount() {
        return userDao.findCount();
    }

    @Override
    public List<User> findByPage(int begin, int limit) {
        return userDao.findByPage(begin, limit);
    }

    @Override
    public User findByUid(Integer uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public void delete(User exitUser) {
        userDao.delete(exitUser);
    }
}
