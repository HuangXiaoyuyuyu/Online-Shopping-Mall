package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.hxy.shop.Utils.PageBean;
import org.hxy.shop.model.Category;
import org.hxy.shop.model.CategorySecond;
import org.hxy.shop.model.Product;
import org.hxy.shop.service.CategorySecondService;
import org.hxy.shop.service.CategoryService;

import java.util.List;

public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond> {
    //模型驱动使用的类
    private CategorySecond categorySecond = new CategorySecond();
    @Override
    public CategorySecond getModel() {
        return categorySecond;
    }
    private CategoryService categoryService;
    private CategorySecondService categorySecondService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setCategorySecondService(CategorySecondService categorySecondService) {
        this.categorySecondService = categorySecondService;
    }

    //接收page
    private Integer page;

    public void setPage(Integer page) {
        this.page = page;
    }

    //查询二级分类的方法
    public String findAllByPage() {
        PageBean<CategorySecond> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setPage(page);
        //System.out.println(page);
        //设置每页的记录
        int limit = 12;
        pageBean.setLimit(limit);
        //设置总记录
        int totalCount = 0;
        totalCount =categorySecondService.findCount();
        //System.out.println(totalCount);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int totalPage = 0;
        totalPage = (totalCount % limit == 0) ? totalCount/limit : totalCount/limit + 1;
        pageBean.setTotalPage(totalPage);
        //每页显示的数据集合
        int begin = (page - 1) * limit;
        List<CategorySecond> categorySeconds = categorySecondService.findByPage(begin,limit);
        pageBean.setList(categorySeconds);
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findAll";
    }

    //跳转到添加页面
    public String addPage() {
        //查询所有一级分类
        List<Category> cList = categoryService.findAllCategory();
        //把数据显示到页面下拉列表--值栈
        ActionContext.getContext().getValueStack().set("cList",cList);
        return "addPageSuccess";
    }

    //保存二级分类的方法
    public String save() {
        categorySecondService.save(categorySecond);
        return "saveSuccess";
    }


    //删除二级分类的方法
    public String delete() {
        //如果级联删除先查询再删除，配置cascade
        categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
        categorySecondService.delete(categorySecond);
        return "deleteSuccess";
    }

    //跳转到修改页面
    public String edit() {
        //根据二级分类的id查询二级分类对象
        categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
        //查询所有一级分类
        List<Category> cList = categoryService.findAllCategory();
        ActionContext.getContext().getValueStack().set("cList",cList);
        return "editSuccess";
    }

    //修改二级分类
    public String update() {
        categorySecondService.update(categorySecond);
        return "updateSuccess";
    }

}
