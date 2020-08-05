package com.hcl.training.service;

import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Product;
import com.hcl.training.model.Seller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductServiceTest {

    private static final Logger logger = LogManager.getLogger(ProductServiceTest.class);

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createProductTest(){
        Product product= getProduct();

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        Product product1 = productService.createProduct(product);
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());

    }

    @Test
    public void getAllProductTest(){
        Product product= getProduct();

        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> product1 = productService.getAllProducts();
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.get(0).getProductName(), product.getProductName());
        Assert.assertEquals(product1.get(0).getProductId(), product.getProductId());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());
    }

    @Test
    public void getProductByIdTest(){
        Product product= getProduct();

        Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(product));

        Product product1 = productService.getProductById(1);
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());
    }

    @Test
    public void getProductByProductCategoryIdTest(){
        Product product= getProduct();

        Mockito.when(productRepository.findByProductCategoryProductCategoryId(Mockito.any(Integer.class))).thenReturn(Arrays.asList(product));

        List<Product> product1 = productService.getProductByProductCategoryId(1);
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.get(0).getProductName(), product.getProductName());
        Assert.assertEquals(product1.get(0).getProductId(), product.getProductId());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.get(0).getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());
    }

    @Test
    public void deleteProductTest(){
        Product product= getProduct();

        Mockito.doNothing().when(productRepository).delete(Mockito.any(Product.class));

        productService.deleteProduct(product);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    private Product getProduct(){
        Product product= new Product();
        product.setProductId(1);
        product.setProductName("Java");
        Seller seller = new Seller();
        seller.setProductSold(10);
        seller.setSellerRegion("Hyd");
        seller.setCountry("India");
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());
        return product;
    }
}
