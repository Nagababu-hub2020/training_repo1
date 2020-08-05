package com.hcl.training.controller;

import com.hcl.training.model.Product;
import com.hcl.training.model.Seller;
import com.hcl.training.service.ProductService;
import com.hcl.training.service.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private static final Logger logger = LogManager.getLogger(SellerController.class);

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductService productService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        if (sellers != null && sellers.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(sellers);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<Seller>());
        }
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<List<Seller>> getSellers(@PathVariable int productId) {
        List<Seller> sellers = sellerService.getSellerByProductId(productId);
        if (sellers != null && sellers.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(sellers);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<Seller>());
        }
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Integer sellerId) {
        Seller seller = sellerService.getSellerById(sellerId);
        if (seller != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(seller);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Seller> addNewSeller(@RequestBody Seller seller) {
        Seller newSeller = sellerService.createSeller( seller);
        if (newSeller  != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newSeller );
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Seller> addNewSeller(@RequestBody Seller seller, @PathVariable int productId) {
        Product product = productService.getProductById(productId);
        if (product == null){
            logger.error("No Product found with productId "+productId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        seller.setProduct(product);
        Seller newSeller = sellerService.createSeller(seller);
        if (newSeller  != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newSeller );
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }

    @PutMapping
    public ResponseEntity<Seller> updateSeller(@RequestBody Seller seller) {
        Seller updatedSeller = sellerService.updateSeller(seller);
        if (updatedSeller != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedSeller);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSeller(@RequestBody Seller seller) {
        sellerService.deleteSeller(seller);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Seller  deleted successfully");
        }
    }
