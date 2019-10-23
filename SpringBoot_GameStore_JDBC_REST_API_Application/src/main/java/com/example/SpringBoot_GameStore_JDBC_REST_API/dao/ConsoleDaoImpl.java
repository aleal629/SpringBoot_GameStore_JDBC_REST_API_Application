package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleDaoImpl implements ConsoleDao {

    private final String INSERT_CONSOLE_SQL =
            "insert into console (model, manufacturer, memory_amount, processor, price, quantity) " +
                    "values (?, ?, ?, ?, ?, ?)";

    private final String SELECT_CONSOLE_SQL =
            "select * from console where console_id = ?";

    private final String SELECT_ALL_CONSOLES_SQL =
            "select * from console";

    private final String UPDATE_CONSOLE_SQL =
            "update console set model = ?, manufacturer = ?, memory_amount = ?, processor = ?, price = ?, quantity = ? " +
                    "where console_id = ?";

    private final String DELETE_CONSOLE_SQL =
            "delete from console where console_id = ?";

    private final String SEARCH_CONSOLE_BY_MANUFACTURER_SQL =
            "select * from console where manufacturer = ?";

    private final String SELECT_QUANTITY_FROM_CONSOLE_SQL =
            "select quantity from console where console_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ConsoleDaoImpl(JdbcTemplate newJdbcTemplate){
        this.jdbcTemplate = newJdbcTemplate;
    }

    private Console mapRowToConsole(ResultSet rs, int rowNum) throws SQLException{
        Console console = new Console();
        console.setConsoleId(rs.getInt("console_id"));
        console.setModel(rs.getString("model"));
        console.setManufacturer(rs.getString("manufacturer"));
        console.setMemoryAmount(rs.getString("memory_amount"));
        console.setProcessor(rs.getString("processor"));
        console.setPrice(rs.getDouble("price"));
        console.setQuantity(rs.getInt("quantity"));
        return console;
    }

    @Override
    @Transactional
    public Console addConsole(Console console) {
        jdbcTemplate.update(INSERT_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        console.setConsoleId(id);
        return console;
    }

    @Override
    public Console getConsole(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CONSOLE_SQL, this::mapRowToConsole, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Console> getAllConsoles() {
        return jdbcTemplate.query(SELECT_ALL_CONSOLES_SQL, this::mapRowToConsole);
    }

    @Override
    public void updateConsole(Console console) {
        jdbcTemplate.update(UPDATE_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsoleId());
    }

    @Override
    public void deleteConsole(int id) {
        jdbcTemplate.update(DELETE_CONSOLE_SQL, id);
    }

    @Override
    public List<Console> searchConsoleByManufacturer(String manufacturer) {
        return jdbcTemplate.query(SEARCH_CONSOLE_BY_MANUFACTURER_SQL, this::mapRowToConsole, manufacturer);
    }

    @Override
    public int getInventoryQuantity(int id) {
        return jdbcTemplate.queryForObject(SELECT_QUANTITY_FROM_CONSOLE_SQL, Integer.class, id);
    }
}
