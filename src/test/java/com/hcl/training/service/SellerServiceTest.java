package com.hcl.training.service;

import com.hcl.training.Repository.SellerRepository;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SellerServiceTest {

    private static final Logger logger = LogManager.getLogger(SellerServiceTest.class);

    @InjectMocks
    SellerService sellerService;

    @Mock
    SellerRepository sellerRepository ;

    @Test
    public void createSellerTest(){
        Seller seller = getSeller();
        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        Seller seller1 = sellerService.createSeller(seller);
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProduct().getProductName(), seller.getProduct().getProductName());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());

    }


    @Test
    public  void updateSellerTest(){

        Seller seller = getSeller();
        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        Seller seller1 = sellerService.updateSeller(seller);
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProduct().getProductName(), seller.getProduct().getProductName());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());

    }
    @Test
    public void getAllSellersTest(){

        Seller seller = getSeller();
        Mockito.when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller));

        List<Seller> seller1 = sellerService.getAllSellers();
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.get(0).getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.get(0).getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.get(0).getProduct().getProductName(), seller.getProduct().getProductName());
        Assert.assertEquals(seller1.get(0).getProductSold(), seller.getProductSold());
    }
    @Test
    public void getSellerByIdTest(){

        Seller seller = getSeller();
        Mockito.when(sellerRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(seller));

        Seller seller1 = sellerService.getSellerById(1);
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProduct().getProductName(), seller.getProduct().getProductName());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }
    @Test
    public void getSellerByProductIdTest(){

        Seller seller = getSeller();
        Mockito.when(sellerRepository.findByProductProductId(Mockito.any(Integer.class))).thenReturn(Arrays.asList(seller));

        List<Seller> seller1 = sellerService.getSellerByProductId(1);
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.get(0).getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.get(0).getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.get(0).getProduct().getProductName(), seller.getProduct().getProductName());
        Assert.assertEquals(seller1.get(0).getProductSold(), seller.getProductSold());



    }
    @Test
    public void deleteSellerTest(){

        Seller seller = getSeller();
        sellerService.deleteSeller(seller);
        Mockito.verify(sellerRepository, Mockito.times(1)).delete(seller);
    }

    private Seller getSeller(){

        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellDate(new Date());
        seller.setSellerId(1);
        seller.setProductSold(5);
        seller.setSellerRegion("Hyderabad");
        Product product = new Product();
        product.setProductName("Java");
        seller.setProduct(product);
        return seller;
    }

}
