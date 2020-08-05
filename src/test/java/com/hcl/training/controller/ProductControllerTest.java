package com.hcl.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.training.TrainingMainApplication;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Product;
import com.hcl.training.model.ProductCategory;
import com.hcl.training.model.Seller;
import com.hcl.training.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrainingMainApplication.class)
@WebAppConfiguration
public class ProductControllerTest {

    private static final Logger logger = LogManager.getLogger(ProductControllerTest.class);

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productService.setProductRepository(productRepository);
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
    public void getProductsList() throws Exception {
        String uri = "/products";
        Product product = getProduct();
        productService.createProduct(product);
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        //objectMapper.readValue(content, List.class);
        Product[] products =  objectMapper.readValue(content, Product[].class); // (content, Product.class);
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(products);
        Product product1 = products[0];
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());
    }

    @Test
    public void getProductByIdTest() throws Exception {
        String uri = "/products/1";
        Product product = getProduct();
        productService.createProduct(product);
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        //objectMapper.readValue(content, List.class);
        Product product1 =  objectMapper.readValue(content, Product.class); // (content, Product.class);
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());

    }

    @Test
    public void addProductWithCategoryTest() throws Exception {
        String uri = "/products/1";
        Product product = getProduct();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        productCategory.setProductCategoryName("Apple");
        product.setProductCategory(productCategory);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(product))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Product product1 =  objectMapper.readValue(content, Product.class); // (content, Product.class);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());
    }

    @Test
    public void updateProductTest() throws Exception {
        String uri = "/products";
        Product product = getProduct();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(product))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        //objectMapper.readValue(content, List.class);
        Product product1 =  objectMapper.readValue(content, Product.class); // (content, Product.class);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());

    }

    @Test
    public void deleteProductTest() throws Exception {
        String uri = "/products";
        Product product = getProduct();
        //Mockito.when(productRepository.delete(Mockito.any(Product.class)));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(product))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        Mockito.verify(productRepository, Mockito.times(1)).delete(Mockito.any(Product.class));
        Assert.assertNotNull(content);

        Assert.assertEquals(content, "Product deleted successfully");
    }

    private Product getProduct(){
        Product product= new Product();
        product.setProductId(1);
        product.setProductName("Apple");
        Seller seller = new Seller();
        seller.setProductSold(20);
        seller.setSellerRegion("Hyd");
        seller.setCountry("India");
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());
        return product;
    }
}