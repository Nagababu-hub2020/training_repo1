package com.hcl.training.service;
import com.hcl.training.Repository.ProductCategoryRepository;
import com.hcl.training.model.Manufacturer;
import com.hcl.training.model.Product;
import com.hcl.training.model.ProductCategory;
import com.hcl.training.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ProductCategoryService.class);

    @Autowired
    ProductCategoryRepository  productCategoryRepository;

    public void setProductCategoryRepository(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public  List<ProductCategory> getProductCategories(){
        return (List<ProductCategory>)  productCategoryRepository.findAll();

    }

    public ProductCategory getProductCategoryById(int productCategoryId ){
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(productCategoryId);
        if(optionalProductCategory .isPresent()) {
            return optionalProductCategory .get();
        }
        else {
            return null;
        }
    }

    public ProductCategory createProductCategory( ProductCategory productCategory){
        List<Product> products = productCategory.getProducts();
        products.forEach(product -> {
            product.setProductCategory(productCategory);
            List<Manufacturer> manufacturers = product.getManufacturers();
            manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
            product.setManufacturers(manufacturers);
            List<Seller> sellers = product.getSellers();
            sellers.forEach(seller -> seller.setProduct(product));
            product.setSellers(sellers);
        });

        productCategory.setProducts(products);
        return  productCategoryRepository.saveAndFlush(productCategory);
    }

    public ProductCategory updateProductCategory( ProductCategory  productCategory){
        return  productCategoryRepository.save(productCategory);
    }

    public void deleteProductCategory( ProductCategory  productCategory){
        productCategoryRepository.delete(productCategory);
    }

}
