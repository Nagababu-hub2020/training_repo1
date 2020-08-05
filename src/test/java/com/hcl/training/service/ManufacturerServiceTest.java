package com.hcl.training.service;

import com.hcl.training.Repository.ManufacturerRepository;
import com.hcl.training.model.Manufacturer;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ManufacturerServiceTest {

    private static final Logger logger = LogManager.getLogger(ManufacturerServiceTest.class);

    @InjectMocks
    ManufacturerService manufacturerService;

    @Mock
    ManufacturerRepository manufacturerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createManufacturerTest(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);
        Manufacturer response = manufacturerService.createManufacturer(manufacturer);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(response.getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(response.getProductCount(), manufacturer.getProductCount());
    }

    @Test
    public void updateManufacturerTest(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);
        Manufacturer response = manufacturerService.createManufacturer(manufacturer);
        response.setProductCount(15);
        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(response);
        Manufacturer manufacturer1 = manufacturerService.updateManufacturer(response);
        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), response.getCountry());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), response.getManufacturerRegion());
        Assert.assertEquals(manufacturer1.getProductCount(), response.getProductCount());
    }

    @Test
    public void deleteManufacturerTest(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.doNothing().when(manufacturerRepository).delete(Mockito.any(Manufacturer.class));
        manufacturerService.deleteManufacturer(manufacturer);
        Mockito.verify(manufacturerRepository, Mockito.times(1)).delete(manufacturer);
    }

    @Test
    public void getManufacturerByIdTest(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.when(manufacturerRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(manufacturer));
        Manufacturer manufacturer1 = manufacturerService.getManufacturerById(1);
        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());
    }

    @Test
    public void getAllManufacturersTest(){
        Manufacturer manufacturer = getManufacturer();
        Mockito.when(manufacturerRepository.findAll()).thenReturn(Arrays.asList(manufacturer));
        List<Manufacturer> manufacturer1 = manufacturerService.getAllManufacturers();
        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.get(0).getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.get(0).getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(manufacturer1.get(0).getProductCount(), manufacturer.getProductCount());
    }

    private Manufacturer getManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(1);
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(new Date());
        manufacturer.setProductCount(10);
        return manufacturer;
    }
}
