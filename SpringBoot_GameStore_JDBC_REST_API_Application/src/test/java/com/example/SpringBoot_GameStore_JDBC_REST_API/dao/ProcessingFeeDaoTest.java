package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProcessingFeeDaoTest {

    @Autowired
    protected ProcessingFeeDao processingFeeDao;

    @Test
    public void getProcessingFee() {
        double tshirtFee = processingFeeDao.getProcessingFee("T-Shirts");
        assertEquals(1.98, tshirtFee, .001);

        double consoleFee = processingFeeDao.getProcessingFee("Consoles");
        assertEquals(14.99, consoleFee, .001);

    }
}