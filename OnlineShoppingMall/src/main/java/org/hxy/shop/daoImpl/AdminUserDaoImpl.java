package org.hxy.shop.daoImpl;

import org.hxy.shop.dao.AdminUserDao;
import org.hxy.shop.model.AdminUser;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    @Override
    public AdminUser login(AdminUser adminUser) {
        String hql = "from AdminUser where username = ? and password = ?";
        List<AdminUser> adminUsers = (List<AdminUser>) hibernateTemplate.find(hql, new Object[]{adminUser.getUsername(), adminUser.getPassword()});
        if (adminUsers!=null && adminUsers.size()>0) {
            return adminUsers.get(0);
        }else{
            return null;
        }
    }
}
