package com.hcl.training.Repository;
import com.hcl.training.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

   List<Product> findByProductCategoryProductCategoryId(int  productCategoryId);
   List<Product> findByProductName(String productName);

}
