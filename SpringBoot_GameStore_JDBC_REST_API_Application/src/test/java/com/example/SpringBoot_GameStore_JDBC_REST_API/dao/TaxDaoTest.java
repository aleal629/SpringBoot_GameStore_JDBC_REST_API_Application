package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaxDaoTest {

    @Autowired
    protected TaxDao taxDao;

    @Test
    public void getStateTax() {
        double alTax = taxDao.getStateTax("AL");
        assertEquals(.05, alTax, .001);

        double nyTax = taxDao.getStateTax("NY");
        assertEquals(.06, nyTax, .001);
    }
}