package org.hxy.shop.service.impl;

import org.hxy.shop.dao.AdminUserDao;
import org.hxy.shop.model.AdminUser;
import org.hxy.shop.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    @Override
    public AdminUser login(AdminUser adminUser) {
        return adminUserDao.login(adminUser);
    }
}
