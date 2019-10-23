package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Tshirt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TshirtDaoTest {

    @Autowired
    protected TshirtDao tshirtDao;

    @Before
    @After
    public void setUp(){
        //Cleans up test DB
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();

        tshirts.stream()
                .forEach(tshirt -> tshirtDao.deleteTshirt(tshirt.getTshirtId()));
    }

    @Test
    public void addGetDeleteTshirt() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();

        //Tests add from helper method and get all
        assertEquals(3, tshirtDao.getAllTshirts().size());

        //Test get
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();
        Tshirt tshirt = tshirts.get(0);
        assertEquals(tshirt, tshirtDao.getTshirt(tshirt.getTshirtId()));

        //Tests delete
        tshirtDao.deleteTshirt(tshirt.getTshirtId());
        assertEquals(2, tshirtDao.getAllTshirts().size());
    }

    @Test
    public void getAllTshirts() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();
        //Tests add from helper method and get all
        assertEquals(3, tshirtDao.getAllTshirts().size());
    }

    @Test
    public void updateTshirt() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();

        //Test update
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();
        Tshirt tshirt = tshirts.get(0);
        tshirt.setSize("Small");
        tshirt.setColor("Red");
        tshirt.setDescription("50% Poly/50% Cotton");
        tshirt.setPrice(7.89);
        tshirt.setQuantity(55);

        tshirtDao.updateTshirt(tshirt);
        assertEquals(tshirt, tshirtDao.getTshirt(tshirt.getTshirtId()));
    }

    @Test
    public void searchTshirtsByColor() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();
        //Test search by color
        assertEquals(2, tshirtDao.searchTshirtsByColor("Black").size());
        assertEquals(1, tshirtDao.searchTshirtsByColor("White").size());
        assertEquals(0, tshirtDao.searchTshirtsByColor("Yellow").size());
    }

    @Test
    public void searchTshirtsBySize() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();
        //Test search by size
        assertEquals(2, tshirtDao.searchTshirtsBySize("Large").size());
        assertEquals(1, tshirtDao.searchTshirtsBySize("Medium").size());
        assertEquals(0, tshirtDao.searchTshirtsBySize("Small").size());
    }

    @Test
    public void getInventoryQuantity() {
        //Loads 3 T-shirts into the database
        helperTshirtDataLoader();
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();

        assertEquals(200, tshirtDao.getInventoryQuantity(tshirts.get(0).getTshirtId()));
        assertEquals(350, tshirtDao.getInventoryQuantity(tshirts.get(1).getTshirtId()));
        assertEquals(150, tshirtDao.getInventoryQuantity(tshirts.get(2).getTshirtId()));
    }

    private void helperTshirtDataLoader(){
        //Load 3 tshirts into the database
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("100% Cotton t-shirt");
        tshirt.setPrice(12.99);
        tshirt.setQuantity(200);
        tshirt = tshirtDao.addTshirt(tshirt);

        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("Large");
        tshirt1.setColor("White");
        tshirt1.setDescription("100% Cotton t-shirt");
        tshirt1.setPrice(9.75);
        tshirt1.setQuantity(350);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        Tshirt tshirt2 = new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("100% Cotton t-shirt");
        tshirt2.setPrice(12.99);
        tshirt2.setQuantity(150);
        tshirt2 = tshirtDao.addTshirt(tshirt2);
    }
}