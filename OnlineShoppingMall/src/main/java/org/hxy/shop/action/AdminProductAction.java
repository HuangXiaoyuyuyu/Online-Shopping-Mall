package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.Utils.PageBean;
import org.hxy.shop.model.CategorySecond;
import org.hxy.shop.model.Product;
import org.hxy.shop.service.CategorySecondService;
import org.hxy.shop.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AdminProductAction extends ActionSupport implements ModelDriven<Product> {
    //模型驱动使用的对象
    private Product product = new Product();
    @Override
    public Product getModel() {
        return product;
    }

    private ProductService productService;
    private CategorySecondService categorySecondService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategorySecondService(CategorySecondService categorySecondService) {
        this.categorySecondService = categorySecondService;
    }

    //接受page
    private Integer page;

    public void setPage(Integer page) {
        this.page = page;
    }

    //文件上传需要的参数：
    private File upload;//上传的文件  名字与表单一致
    private String uploadFileName;//接受文件上传的文件名
    private String uploadContextType;//接受文件上传的文件MIME的类型

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setUploadContextType(String uploadContextType) {
        this.uploadContextType = uploadContextType;
    }

    //带分页的查询商品的执行的方法
    public String findAllByPage() {
        PageBean<Product> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setPage(page);
        //System.out.println(page);
        //设置每页的记录
        int limit = 10;
        pageBean.setLimit(limit);
        //设置总记录
        int totalCount = 0;
        totalCount =productService.findCount();
        //System.out.println(totalCount);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int totalPage = 0;
        totalPage = (totalCount % limit == 0) ? totalCount/limit : totalCount/limit + 1;
        pageBean.setTotalPage(totalPage);
        //每页显示的数据集合
        int begin = (page - 1) * limit;
        List<Product> products = productService.findByPage(begin,limit);
        pageBean.setList(products);
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findAll";
    }

    //跳转到添加页面
    public String addPage() {
        //查询所有的二级分类集合
        List<CategorySecond> csList = categorySecondService.findAll();
        //通过值栈保存数据
        ActionContext.getContext().getValueStack().set("csList",csList);
        return "addPageSuccess";
    }

    //保存商品的方法
    public String save() throws IOException {
        //调用service完成保存
        product.setPdate(new Date());
        if (upload != null) {
            //获取文件上传的磁盘绝对路径
            String realPath = ServletActionContext.getServletContext()
                    .getRealPath("/products");
            //创建一个文件
            File diskFile = new File(realPath+"//"+uploadFileName);
            //文件上传
            FileUtils.copyFile(upload,diskFile);
            product.setImage("products/"+uploadFileName);
        }
        productService.save(product);
        return "saveSuccess";
    }

    //删除商品的方法
    public String delete() {
        //先查询再删除
        product = productService.findByPid(product.getPid());
        //设置ManyToOne的One为null(非级联删除)
        product.setCategorySecond(null);
        productService.update(product);
        //删除上传的图片
        String path = product.getImage();
        if (path != null) {
            String realPath = ServletActionContext.getServletContext()
                    .getRealPath("/" + path);
            File file = new File(realPath);
            file.delete();
        }
        //删除商品
        productService.delete(product);
        return "deleteSuccess";
    }

    //编辑商品的方法
    public String edit() {
        //根据商品的id查询商品
        product = productService.findByPid(product.getPid());
        //查询所有的二级分类
        List<CategorySecond> csList = categorySecondService.findAll();
        ServletActionContext.getContext().getValueStack().set("csList",csList);
        return "editSuccess";
    }

    //修改商品的方法
    public String update() throws IOException {
        product.setPdate(new Date());
        //文件上传
        if (upload != null) {
            //删除原来的图片
            String path = product.getImage();
            String realPathOrigin = ServletActionContext.getServletContext()
                    .getRealPath("/" + path);
            File file = new File(realPathOrigin);
            file.delete();
            //上传新的图片
            String realPath = ServletActionContext.getServletContext()
                    .getRealPath("/products");
            File diskFile = new File(realPath+"//"+uploadFileName);
            FileUtils.copyFile(upload,diskFile);
            product.setImage("products/"+uploadFileName);
        }
        //修改商品数据到数据库
        productService.update(product);
        return "updateSuccess";
    }
}
