package org.hxy.shop.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorysecond")
public class CategorySecond {
    private int csid;
    private String csname;
    private Category category;
    private Set<Product> products = new HashSet<>();
    @Id
    @GeneratedValue
    public int getCsid() {
        return csid;
    }

    public void setCsid(int csid) {
        this.csid = csid;
    }

    public String getCsname() {
        return csname;
    }

    public void setCsname(String csname) {
        this.csname = csname;
    }
    @ManyToOne
    @JoinColumn(name = "cid")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }
    @OneToMany(mappedBy = "categorySecond",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
