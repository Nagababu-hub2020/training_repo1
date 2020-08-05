package com.hcl.training.service;

import com.hcl.training.Repository.ManufacturerRepository;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.controller.ProductCategoryController;
import com.hcl.training.model.Manufacturer;
import com.hcl.training.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private Logger logger = (Logger) LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    ProductService productService;

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setManufacturerRepository(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Product> searchProductsByName( String productName ){
        return  productRepository.findByProductName( productName );
    }

    public List<Product> searchProductsByRegion(String manufacturerRegion ){
        List<Product> products = new ArrayList<Product>();
        List<Manufacturer> productManufacturers = getManufacturerByRegion( manufacturerRegion );
        List<Integer> productIds = new ArrayList<Integer>();
        for(Manufacturer manufacturer:productManufacturers){
            if( manufacturer!=null && manufacturer.getProduct()!=null) {
                int productId = manufacturer.getProduct().getProductId();
                productIds.add(productId);
            }
        }
        for(int productId :productIds){
          Product product = productService.getProductById(productId) ;
          products.add(product);
        }
        return  products;
    }

    public List<Manufacturer> getManufacturerByRegion(String manufacturerRegion ){
        return  manufacturerRepository.findByManufacturerRegion( manufacturerRegion );

    }
}
