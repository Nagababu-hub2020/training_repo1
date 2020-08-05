package com.hcl.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.training.TrainingMainApplication;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.Repository.SellerRepository;
import com.hcl.training.model.Product;
import com.hcl.training.model.Seller;

import com.hcl.training.service.ProductService;
import com.hcl.training.service.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrainingMainApplication.class)
@WebAppConfiguration
public class SellerControllerTest {
    private static final Logger logger = LogManager.getLogger(SellerControllerTest.class);
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    ProductRepository productRepository;

    @Mock
    SellerRepository sellerRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductService productService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productService.setProductRepository(productRepository);
        sellerService.setSellerRepository(sellerRepository);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void getSellersTest() throws Exception {
        String uri = "/sellers";
        Seller seller = getSeller();

        Mockito.when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Seller[] sellers =  mapFromJson(content, Seller[].class);

        Mockito.verify(sellerRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(sellers);
        Seller seller1 = sellers[0];
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }

    @Test
    public void getSellersByProductIdTest() throws Exception {
        String uri = "/sellers/products/1";
        Seller seller = getSeller();

        Mockito.when(sellerRepository.findByProductProductId(Mockito.anyInt())).thenReturn(Arrays.asList(seller));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Seller[] sellers =  mapFromJson(content, Seller[].class);

        Mockito.verify(sellerRepository, Mockito.times(1)).findByProductProductId(Mockito.anyInt());
        Assert.assertNotNull(sellers);
        Seller seller1 = sellers[0];
        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }

    @Test
    public void getSellersBySellerIdTest() throws Exception {
        String uri = "/sellers/1";
        Seller seller = getSeller();

        Mockito.when(sellerRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(seller));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Seller seller1 =  mapFromJson(content, Seller.class);

        Mockito.verify(sellerRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assert.assertNotNull(seller1);

        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }

    @Test
    public void createSellerTest() throws Exception {
        String uri = "/sellers";
        Seller seller = getSeller();
        Product product = getProduct();
        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);
        // Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(product));


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(seller))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Seller seller1 =  mapFromJson(content, Seller.class);

        Mockito.verify(sellerRepository, Mockito.times(1)).save(Mockito.any(Seller.class));
        Assert.assertNotNull(seller1);

        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }

    @Test
    public void updateSellerTest() throws Exception {
        String uri = "/sellers";
        Seller seller = getSeller();

        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(seller))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Seller seller1 =  mapFromJson(content, Seller.class);

        Mockito.verify(sellerRepository, Mockito.times(1)).save(Mockito.any(Seller.class));
        Assert.assertNotNull(seller1);

        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), seller.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
    }

    @Test
    public void deleteSellerTest() throws Exception {
        String uri = "/sellers";
        Seller seller = getSeller();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(seller))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Mockito.verify(sellerRepository, Mockito.times(1)).delete(Mockito.any(Seller.class));
        Assert.assertNotNull(content);
        Assert.assertEquals("Seller  deleted successfully", content);
    }

    private Seller getSeller(){

        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellDate(new Date());
        seller.setSellerId(1);
        seller.setProductSold(5);
        seller.setSellerRegion("Hyderabad");
        Product product = getProduct();
        product.setSellers(Arrays.asList(seller));
        seller.setProduct(product);
        return seller;
    }
    private Product getProduct(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Java");
        return product;
    }
}
