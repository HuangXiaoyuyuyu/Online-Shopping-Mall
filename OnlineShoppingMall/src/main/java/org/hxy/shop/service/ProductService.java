package org.hxy.shop.service;

import org.hxy.shop.model.Product;

import java.util.List;

public interface ProductService {
    public List findAllHot();
    public List findAllNew();
    public Product findByPid(int pid);
    public int findCountCid(int cid);
    public int findCountCsid(int csid);
    public List findByPageCid(int cid, int begin, int limit);
    public List findByPageCsid(int csid, int begin, int limit);
    public int findCount();
    public List<Product> findByPage(int begin, int limit);
    public void save(Product product);
    public void delete(Product product);
    public void update(Product product);
}
