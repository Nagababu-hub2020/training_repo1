package com.hcl.training.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductCategory {

 @Id
 //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCategoryId;
    private  String productCategoryName;
    private String productCategoryDescription;
    @OneToMany(mappedBy = "productCategory" , cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     private List<Product> products = new ArrayList<Product>();


    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductCategoryDescription() {
        return productCategoryDescription;
    }

    public void setProductCategoryDescription(String productCategoryDescription) {
        this.productCategoryDescription = productCategoryDescription;
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
