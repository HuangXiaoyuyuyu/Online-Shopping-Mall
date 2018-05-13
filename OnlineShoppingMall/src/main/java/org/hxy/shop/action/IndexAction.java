package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.hxy.shop.dao.ProductDao;
import org.hxy.shop.model.Category;
import org.hxy.shop.model.Product;
import org.hxy.shop.service.CategoryService;
import org.hxy.shop.service.ProductService;

import java.util.List;

/**
 * 首页访问Action
 */
public class IndexAction extends ActionSupport {
    private CategoryService categoryService;
    private ProductService productService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute() throws Exception {
        //一级目录查询
        List<Category> categories = categoryService.findAllCategory();
        //一级目录存入Session
        ActionContext.getContext().getSession().put("categories",categories);
        //热门商品
        List<Product> hotP = productService.findAllHot();
        //保存到值栈
        ActionContext.getContext().getValueStack().set("hotP",hotP);
        //最新商品
        List<Product> newP = productService.findAllNew();
        //保存到值栈
        ActionContext.getContext().getValueStack().set("newP",newP);
        return "index";
    }
}
