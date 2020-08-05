package com.hcl.training.Repository;

import com.hcl.training.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Integer> {
    public List<Seller> findByProductProductId(int productId);
}
