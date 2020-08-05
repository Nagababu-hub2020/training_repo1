package com.hcl.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int sellerId;
     private String sellerRegion;
    private  String country ;
     private int productSold;
    private Date sellDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "productId")
    private Product product;

   public String getSellerRegion() {
       return sellerRegion;
   }

    public void setSellerRegion(String sellerRegion) {
        this.sellerRegion = sellerRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProductSold() {
        return productSold;
    }

    public void setProductSold(int productSold) {
        this.productSold = productSold;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

}
