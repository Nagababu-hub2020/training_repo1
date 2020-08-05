package com.hcl.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int manufacturerId;
     private String manufacturerRegion;
     private String country;
     private int productCount;
    private Date manufacturingDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "productId")
    private Product product;

    public String getManufacturerRegion() {
        return manufacturerRegion;
    }

    public void setManufacturerRegion(String manufacturerRegion) {
        this.manufacturerRegion = manufacturerRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

   public Product getProduct() {
       return product;
   }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }



}
