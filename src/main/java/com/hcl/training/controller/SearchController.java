package com.hcl.training.controller;

import com.hcl.training.model.Product;
import com.hcl.training.service.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private static final Logger logger = LogManager.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    @GetMapping("/products/{productName}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName){
        logger.info("getProductByName() <<");
     List<Product> existingProducts =searchService.searchProductsByName(productName);
     if(existingProducts!=null && existingProducts.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( existingProducts);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<Product>());
        }
    }

    @GetMapping("/manufacturers/{manufacturerRegion}")
    public ResponseEntity<List<Product>> getProductByRegion(@PathVariable String manufacturerRegion){
        List<Product> products =searchService.searchProductsByRegion(manufacturerRegion);
        if(products!=null && products.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( products);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ArrayList<Product>());
        }

    }


}
