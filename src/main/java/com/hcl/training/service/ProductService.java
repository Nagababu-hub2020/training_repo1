package com.hcl.training.service;
import java.util.List;
import java.util.Optional;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository ;


    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct( Product product){
        return  productRepository.save(product);
    }

    public Product updateProduct( Product product){
        return  productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }

    public Product getProductById(int productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        else {
            return null;
        }
    }

    public List<Product> getProductByProductCategoryId(int productCategoryId){
        List<Product> products = productRepository.findByProductCategoryProductCategoryId(productCategoryId);
        return products;
    }

    public void deleteProduct( Product product){
        productRepository.delete(product);
    }
}
