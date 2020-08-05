package com.hcl.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.training.TrainingMainApplication;
import com.hcl.training.Repository.ManufacturerRepository;
import com.hcl.training.Repository.ProductRepository;
import com.hcl.training.model.Manufacturer;
import com.hcl.training.model.Product;
import com.hcl.training.service.ManufacturerService;
import com.hcl.training.service.ProductService;

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
public class ManufacturerControllerTest {

    private static final Logger logger = LogManager.getLogger(ManufacturerControllerTest.class);

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
    ProductService productService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productService.setProductRepository(productRepository);
        manufacturerService.setManufacturerRepository(manufacturerRepository);
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
    public void createManufacturerTest() throws Exception {
        String uri = "/products/1/manufacturers";
        Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();
        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(manufacturer))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Manufacturer manufacturer1 =  mapFromJson(content, Manufacturer.class);

        Mockito.verify(manufacturerRepository, Mockito.times(1)).save(Mockito.any(Manufacturer.class));
        Assert.assertNotNull(manufacturer1);

        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
    }

    @Test
    public void updateManufacturerTest() throws Exception {
        String uri = "/manufacturers";
        Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();
        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(manufacturer))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Manufacturer manufacturer1 =  mapFromJson(content, Manufacturer.class);

        Mockito.verify(manufacturerRepository, Mockito.times(1)).save(Mockito.any(Manufacturer.class));
        Assert.assertNotNull(manufacturer1);

        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
    }

    @Test
    public void deleteManufacturerTest() throws Exception {
        String uri = "/manufacturers";
        Manufacturer manufacturer = getManufacturer();
        //Product product = getProduct();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(manufacturer))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(manufacturerRepository, Mockito.times(1)).delete(Mockito.any(Manufacturer.class));
        Assert.assertEquals(" Manufacturer deleted successfully", content);
    }

    @Test
    public void findManufacturerByIdTest() throws Exception {
        String uri = "/manufacturers/1";
        Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();
        Mockito.when(manufacturerRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(manufacturer));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(manufacturer))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Manufacturer manufacturer1 =  mapFromJson(content, Manufacturer.class);

        Mockito.verify(manufacturerRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assert.assertNotNull(manufacturer1);

        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
    }

  @Test
  public void getManufacturerByProductId() throws Exception {
        String uri = "/products/1/manufacturers"; //products/{productId}/manufacturers
        Manufacturer manufacturer = getManufacturer();
        Product product = getProduct();
        Mockito.when(manufacturerRepository.findByProductProductId(Mockito.anyInt())).thenReturn(Arrays.asList(manufacturer));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Manufacturer[] manufacturers =  mapFromJson(content, Manufacturer[].class);

        Mockito.verify(manufacturerRepository, Mockito.times(1)).findByProductProductId(Mockito.any(Integer.class));
        Assert.assertNotNull(manufacturers);
        Manufacturer manufacturer1 = manufacturers[0];
        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
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
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Java");
        return product;
    }



}
