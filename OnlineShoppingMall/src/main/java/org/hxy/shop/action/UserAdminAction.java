package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.hxy.shop.Utils.PageBean;
import org.hxy.shop.model.User;
import org.hxy.shop.service.UserService;

import java.util.List;

/**
 * 后台用户管理的Action类
 *
 */
public class UserAdminAction extends ActionSupport implements ModelDriven<User>{
	// 模型驱动使用的类
	private User user = new User();

	public User getModel() {
		return user;
	}
	
	// 注入用户的Service
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 接受page参数
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 后台查询所有用户的方法带分页:
	public String findAllByPage(){
		PageBean<User> pageBean = new PageBean<>();
		//设置当前页
		pageBean.setPage(page);
		//System.out.println(page);
		//设置每页的记录
		int limit = 10;
		pageBean.setLimit(limit);
		//设置总记录
		int totalCount = 0;
		totalCount =userService.findCount();
		//System.out.println(totalCount);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage = (totalCount % limit == 0) ? totalCount/limit : totalCount/limit + 1;
		pageBean.setTotalPage(totalPage);
		//每页显示的数据集合
		int begin = (page - 1) * limit;
		List<User> users = userService.findByPage(begin,limit);
		pageBean.setList(users);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	// 后台用户的删除
	public String delete(){
		User existUser = userService.findByUid(user.getUid());
		userService.delete(existUser);
		return "deleteSuccess";
	}
	
	// 后台用户的编辑
	public String edit(){
		user = userService.findByUid(user.getUid());
		return "editSuccess";
	}
	
	// 后台用户的修改:
	public String update(){
		userService.update(user);
		return "updateSuccess";
	}
}
