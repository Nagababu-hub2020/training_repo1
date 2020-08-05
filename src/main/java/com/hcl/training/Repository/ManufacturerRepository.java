package com.hcl.training.Repository;

import com.hcl.training.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    List<Manufacturer> findByProductProductId(int productId);
    List<Manufacturer> findByManufacturerRegion(String manufacturerRegion);
}
