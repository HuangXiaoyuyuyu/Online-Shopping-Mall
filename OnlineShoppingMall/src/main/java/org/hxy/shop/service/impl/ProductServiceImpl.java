package org.hxy.shop.service.impl;

import org.hxy.shop.dao.ProductDao;
import org.hxy.shop.model.Product;
import org.hxy.shop.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public List findAllHot() {
        return productDao.findAllHot();
    }

    @Override
    public List findAllNew() {
        return productDao.findAllNew();
    }

    @Override
    public Product findByPid(int pid) {
        return productDao.findByPid(pid);
    }

    @Override
    public int findCountCid(int cid) {
        return productDao.findCountCid(cid);
    }

    @Override
    public int findCountCsid(int csid) {
        return productDao.findCountCsid(csid);
    }

    @Override
    public List findByPageCid(int cid, int begin, int limit) {
        return productDao.findByPageCid(cid,begin,limit);
    }

    @Override
    public List findByPageCsid(int csid, int begin, int limit) {
        return productDao.findByPageCsid(csid,begin,limit);
    }

    @Override
    public int findCount() {
        return productDao.findCount();
    }

    @Override
    public List<Product> findByPage(int begin, int limit) {
        return productDao.findByPage(begin,limit);
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }
}
