package com.hcl.training.controller;

import com.hcl.training.model.Product;

import java.util.ArrayList;
import java.util.List;

import com.hcl.training.model.ProductCategory;
import com.hcl.training.service.ProductCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productCategories")
public class ProductCategoryController {

    private static final Logger logger = LogManager.getLogger(ProductCategoryController.class);

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getProductCategories(){
     List<ProductCategory> existingProductCategories= productCategoryService.getProductCategories();


     if(existingProductCategories!=null && existingProductCategories.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( existingProductCategories);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<ProductCategory>());
        }

    }


    @GetMapping("{productCategoryId}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Integer productCategoryId) {

        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);

        if (productCategory != null) {
            List<Product> products = productCategory.getProducts();
            products.forEach( product -> logger.info(product.getProductName()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productCategory);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }


    @PostMapping()
    public ResponseEntity<ProductCategory> addProductCategory(@RequestBody ProductCategory productCategory) {

        ProductCategory prodCategory = productCategoryService.createProductCategory( productCategory);

        if (prodCategory != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productCategory);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }


    @PutMapping()
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productCategory) {

        ProductCategory updatedProductCategory = productCategoryService.updateProductCategory( productCategory);

        if (updatedProductCategory != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedProductCategory);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }


    @DeleteMapping()
    public ResponseEntity<String> deleteProductCategory(@RequestBody ProductCategory productCategory) {

        productCategoryService.deleteProductCategory( productCategory);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product category  deleted successfully");
    }


}
