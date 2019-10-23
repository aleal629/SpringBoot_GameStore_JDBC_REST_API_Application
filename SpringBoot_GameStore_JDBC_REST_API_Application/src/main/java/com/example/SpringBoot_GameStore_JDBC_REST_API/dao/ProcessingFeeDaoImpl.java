package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessingFeeDaoImpl implements ProcessingFeeDao {

    private final String SELECT_PROCESSING_FEE_SQL =
            "select fee from processing_fee where product_type = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProcessingFeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getProcessingFee(String itemType) {
        return jdbcTemplate.queryForObject(SELECT_PROCESSING_FEE_SQL, Double.class, itemType);
    }
}
