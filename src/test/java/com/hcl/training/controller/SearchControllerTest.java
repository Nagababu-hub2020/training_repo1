package com.hcl.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.training.TrainingMainApplication;
import com.hcl.training.Repository.ManufacturerRepository;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Manufacturer;
import com.hcl.training.model.Product;
import com.hcl.training.model.Seller;
import com.hcl.training.service.ManufacturerService;
import com.hcl.training.service.ProductService;
import com.hcl.training.service.SearchService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrainingMainApplication.class)
@WebAppConfiguration
public class SearchControllerTest {
    private static final Logger logger = LogManager.getLogger(SearchControllerTest.class);
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    ProductRepository productRepository;

    @Mock
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    SearchService searchService;

    @Autowired
    ProductService productService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        searchService.setProductRepository(productRepository);
        searchService.setManufacturerRepository(manufacturerRepository);
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
    public void getProductByNameTest() throws Exception {
        String uri = "/search/products/Java";
       // Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();
        Mockito.when(productRepository.findByProductName(Mockito.anyString())).thenReturn(Arrays.asList(product));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Mockito.verify(productRepository, Mockito.times(1)).findByProductName(Mockito.anyString());

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        //objectMapper.readValue(content, List.class);
        Product[] products =  objectMapper.readValue(content, Product[].class); // (content, Product.class);
        Assert.assertNotNull(products);
        Product product1 = products[0];
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());

    }


    @Test
    public void getProductByRegion() throws Exception {
        String uri = "/search/manufacturers/Hyd";
         Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();

        Mockito.when( manufacturerRepository.findByManufacturerRegion(Mockito.anyString())).thenReturn(Arrays.asList(manufacturer));

        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(product));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        //objectMapper.readValue(content, List.class);
        Product[] products =  objectMapper.readValue(content, Product[].class); // (content, Product.class);


        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assert.assertNotNull(products);
        Product product1 = products[0];
        Assert.assertEquals(product1.getProductName(), product.getProductName());
        Assert.assertEquals(product1.getProductId(), product.getProductId());
        Assert.assertEquals(product1.getSellers().get(0).getProductSold(), product.getSellers().get(0).getProductSold());
        Assert.assertEquals(product1.getSellers().get(0).getSellerRegion(), product.getSellers().get(0).getSellerRegion());
        Assert.assertEquals(product1.getSellers().get(0).getCountry(), product.getSellers().get(0).getCountry());

    }

    private Manufacturer getManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(1);
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(new Date());
        manufacturer.setProductCount(10);
        Product product = getProduct();
        product.setManufacturers(Arrays.asList(manufacturer));
        manufacturer.setProduct(product);
        return manufacturer;
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
