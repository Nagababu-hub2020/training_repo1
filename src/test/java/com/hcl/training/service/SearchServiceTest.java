package com.hcl.training.service;

import com.hcl.training.Repository.ManufacturerRepository;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Manufacturer;
import com.hcl.training.model.Product;
import com.hcl.training.model.Seller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SearchServiceTest {

    private static final Logger logger = LogManager.getLogger(SearchServiceTest.class);

    @InjectMocks
    private SearchService searchService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    ProductService productService;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void searchProductsByName(){
        Product product = getProduct();
        Mockito.when(productRepository.findByProductName(Mockito.anyString())).thenReturn(Arrays.asList(product));
        List<Product> product1 = searchService.searchProductsByName("Java");
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.get(0).getProductName(), product.getProductName());
        Assert.assertEquals(product1.get(0).getProductId(), product.getProductId());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
    }

    @Test
    public void searchProductsByRegion(){
        Product product = getProduct();
        Mockito.when(manufacturerRepository.findByManufacturerRegion(Mockito.anyString())).thenReturn(Arrays.asList(getManufacturer()));
        Mockito.when(productService.getProductById(Mockito.anyInt())).thenReturn(product);
        List<Product> products = searchService.searchProductsByRegion("Hyd");
        Assert.assertNotNull(products);
        Assert.assertEquals(products.get(0).getProductName(), product.getProductName());
        Assert.assertEquals(products.get(0).getProductId(), product.getProductId());
        Assert.assertEquals(products.get(0).getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());

    }

    @Test
    public void getManufacturerByRegion(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.when(manufacturerRepository.findByManufacturerRegion(Mockito.anyString())).thenReturn(Arrays.asList(getManufacturer()));
        List<Manufacturer> manufacturers = searchService.getManufacturerByRegion("Hyd");
        Assert.assertNotNull(manufacturers);
        Assert.assertEquals(manufacturers.get(0).getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturers.get(0).getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(manufacturers.get(0).getProductCount(), manufacturer.getProductCount());

    }

    private Product getProduct(){
        Product product= new Product();
        product.setProductId(1);
        product.setProductName("Java");
        Seller seller = new Seller();
        seller.setProductSold(10);
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());
        return product;
    }

    private Manufacturer getManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(1);
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(new Date());
        manufacturer.setProductCount(10);
        manufacturer.setProduct(getProduct());
        return manufacturer;
    }
}
