package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaxDaoImpl implements TaxDao {

    private final String SELECT_STATE_TAX_RATE_SQL =
            "select rate from sales_tax_rate where state = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaxDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getStateTax(String state) {
        return jdbcTemplate.queryForObject(SELECT_STATE_TAX_RATE_SQL, Double.class, state);
    }
}
