package org.hxy.shop.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.model.AdminUser;

/**
 * 后台权限校验拦截器
 * 对没有登录的用户不可以进行访问
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor {

    //执行拦截的方法
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        //判断session中是否保存了后台用户信息
        AdminUser exitAdminUser = (AdminUser) ServletActionContext.getRequest().getSession()
                .getAttribute("exitAdminUser");
        if (exitAdminUser == null) {
            //没有登录就进行访问
            ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
            actionSupport.addActionError("亲！您还没有登录！没有权限访问！");
            return "loginFail";
        }else {
            //已经登录过
            return actionInvocation.invoke();
        }
    }
}
