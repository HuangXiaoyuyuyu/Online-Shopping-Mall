package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.model.AdminUser;
import org.hxy.shop.service.AdminUserService;

/**
 *后台登录
 */
public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {
    //模型驱动使用的对象
    private AdminUser adminUser = new AdminUser();
    private AdminUserService adminUserService;

    public void setAdminUserService(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Override
    public AdminUser getModel() {
        return adminUser ;
    }

    //后台登录
    public String login() {
        AdminUser exitAdminUser = adminUserService.login(adminUser);
        if (exitAdminUser == null) {
            //登录失败
            this.addActionError("亲！您的用户名或者密码错误！");
            return "loginFail";
        }else {
            //登陆成功
            ServletActionContext.getRequest().getSession().setAttribute("exitAdminUser",exitAdminUser);
            return "loginSuccess";
        }
    }

}
