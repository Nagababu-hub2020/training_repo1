package com.hcl.training.controller;

import com.hcl.training.model.Product;
import com.hcl.training.model.ProductCategory;
import com.hcl.training.service.ManufacturerService;
import com.hcl.training.service.ProductCategoryService;
import com.hcl.training.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ManufacturerService manufacturerService ;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> existingProducts = productService.getAllProducts();

        if (existingProducts != null && existingProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(existingProducts);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<Product>());
        }
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductByProductId(@PathVariable  Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(product);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }

    @PostMapping("/{productCategoryId}")
    public ResponseEntity<Product> addProductWithCategory(@RequestBody Product product, @PathVariable Integer productCategoryId ) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(productCategoryId);
        product.setProductCategory(productCategory);
        Product createdProduct = productService.createProduct( product);
        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(createdProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        Product updatedProduct = productService.updateProduct( product);

        if (updatedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {

         productService.deleteProduct( product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product deleted successfully");
    }

    @GetMapping("/productCategories/{productCategoryId}")
    public List<Product> getAllProductByProductCategoryId(@PathVariable (value = "productCategoryId") int productCategoryId) {
        return productService.getProductByProductCategoryId(productCategoryId);
    }

    @PostMapping("/productCategories/{productCategoryId}")
    public Product createProduct(@PathVariable (value = "productCategoryId") int  productCategoryId, @RequestBody Product product) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        product.setProductCategory(productCategory);
        return productService.createProduct(product);
    }
    }


