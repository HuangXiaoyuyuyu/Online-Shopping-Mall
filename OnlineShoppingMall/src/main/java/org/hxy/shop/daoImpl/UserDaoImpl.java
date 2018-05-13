package org.hxy.shop.daoImpl;

import org.hxy.shop.Utils.MailUtils;
import org.hxy.shop.Utils.PageHibernateCallback;
import org.hxy.shop.dao.UserDao;
import org.hxy.shop.model.User;
import org.hxy.shop.Utils.UUIDUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

    @Resource
    private HibernateTemplate hibernateTemplate;

    //根据用户名查找用户
    @Override
    public User findByName(String username) {
        String hql = "from User where username = ?";
        List<User> users = (List<User>) hibernateTemplate.find(hql,username);
        if (users != null &&  users.size()>0) {
            return users.get(0);
        } else
            return null;
    }

    @Override
    public User findByCode(String code) {
        String hql = "from User where code = ?";
        List<User> user = (List<User>) hibernateTemplate.find(hql,code);
        if (user != null && user.size() > 0){
            return user.get(0);
        } else
            return null;
    }

    //用户注册
    @Override
    public void save(User user) {
        user.setState(0);//0代表用户未激活   1代表用户已经激活
        String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
        user.setCode(code);
        hibernateTemplate.save(user);
        MailUtils.sendMail(user.getEmail(),code);
    }

    @Override
    public void update(User user) {
        hibernateTemplate.update(user);
    }

    @Override
    public User login(User user) {
        String hql = "from User where username = ? and password = ? and state = ?";
        List<User> users = (List<User>) hibernateTemplate.find(hql,new Object[]{user.getUsername(),user.getPassword(),1});
        if (users != null && users.size() > 0) {
            return users.get(0);
        }else
            return null;
    }

    //查找用户数量
    @Override
    public int findCount() {
        String hql = "select count(*) from User";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    //对用户进行分页查询
    @Override
    public List<User> findByPage(int begin, int limit) {
        String hql = "from User";
        List<User> list = (List<User>) hibernateTemplate.execute(new PageHibernateCallback<User>(hql,null,begin,limit));
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    //通过uid查询用户
    @Override
    public User findByUid(Integer uid) {
        return hibernateTemplate.get(User.class,uid);
    }

    //删除用户
    @Override
    public void delete(User exitUser) {
        hibernateTemplate.delete(exitUser);
    }
}
