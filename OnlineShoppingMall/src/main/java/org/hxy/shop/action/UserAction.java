package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.model.User;
import org.hxy.shop.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();
    private UserService userService;
    private String checkcode;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Override
    public User getModel() {
        return user;
    }

    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    //跳转到注册界面
    public String registPage() {
        return "registPage";
    }

    //ajax进行异步校验用户名执行的方法
    public String findByName() throws IOException {
        User exitUser = userService.findByName(user.getUsername());
        //获得response对象，向页面输出
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        if (exitUser != null) {
            response.getWriter().println("<font color='red'>用户名已存在</font>");
        } else {
            response.getWriter().println("<font color='green'>用户名可以使用</font>");
        }
        return NONE;
    }

    //用户注册
    public String regist() {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
        if (!code.equalsIgnoreCase(checkcode)) {
            this.addActionError("验证码错误，请重新注册！");
            return "checkcodeFailWhenRegist";
        } else {
            userService.save(user);
            this.addActionMessage("注册成功，请去邮箱激活!");
            return "msg";
        }
    }

    //用户激活
    public String active() {
        //根据激活码查找用户
        User existUser = userService.findByCode(user.getCode());
        if (existUser == null) {
            this.addActionMessage("激活失败！");
        }else {
            existUser.setState(1);
            existUser.setCode(null);
            userService.update(existUser);
            this.addActionMessage("激活成功！请去登录！");
        }
        return "msg";
    }

    //跳转到登录界面
    public String loginPage() {
        return "loginPage";
    }

    //登录
    public String login() {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
        if (!code.equalsIgnoreCase(checkcode)){
            this.addActionError("验证码错误，请重新登录！");
            return "checkcodeFailWhenLogin";
        } else {
            User existUser = userService.login(user);
            if (existUser == null) {
                this.addActionError("登录失败，用户名或密码不正确或用户未激活！");
                return LOGIN;
            } else {
                //登陆成功
                //将用户信息存到session中
                ServletActionContext.getRequest().getSession().setAttribute("existUser",existUser);
                return "loginSuccess";
            }
        }
    }

    //用户的退出 session的销毁
    public String logout(){
        ServletActionContext.getRequest().getSession().invalidate();
        return "logout";
    }
}
