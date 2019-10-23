package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Tshirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TshirtDaoImpl implements TshirtDao {

    private final String INSERT_TSHIRT_SQL =
            "insert into t_shirt (size, color, description, price, quantity) " +
                    "values (?, ?, ?, ?, ?)";

    private final String SELECT_TSHIRT_SQL =
            "select * from t_shirt where t_shirt_id = ?";

    private final String SELECT_ALL_TSHIRTS_SQL =
            "select * from t_shirt";

    private final String UPDATE_TSHIRT_SQL =
            "update t_shirt set size = ?, color = ?, description = ?, price = ?, quantity = ? " +
                    "where t_shirt_id = ?";

    private final String DELETE_TSHIRT_SQL =
            "delete from t_shirt where t_shirt_id = ?";

    private final String SEARCH_TSHIRT_BY_SIZE =
            "select * from t_shirt where size = ?";

    private final String SEARCH_TSHIRT_BY_COLOR =
            "select * from t_shirt where color = ?";

    private final String SELECT_QUANTITY_FROM_TSHIRT_SQL =
            "select quantity from t_shirt where t_shirt_id = ?";

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public TshirtDaoImpl(JdbcTemplate newJdbcTemplate){
        this.jdbcTemplate= newJdbcTemplate;
    }

    private Tshirt mapRowToTshirt(ResultSet rs, int rowNum) throws SQLException{
        Tshirt tshirt = new Tshirt();
        tshirt.setTshirtId(rs.getInt("t_shirt_id"));
        tshirt.setSize(rs.getString("size"));
        tshirt.setColor(rs.getString("color"));
        tshirt.setDescription(rs.getString("description"));
        tshirt.setPrice(rs.getDouble("price"));
        tshirt.setQuantity(rs.getInt("quantity"));
        return tshirt;
    }

    @Override
    @Transactional
    public Tshirt addTshirt(Tshirt tshirt) {
        jdbcTemplate.update(INSERT_TSHIRT_SQL,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        tshirt.setTshirtId(id);
        return tshirt;
    }

    @Override
    public Tshirt getTshirt(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_TSHIRT_SQL, this::mapRowToTshirt, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tshirt> getAllTshirts() {
        return jdbcTemplate.query(SELECT_ALL_TSHIRTS_SQL, this::mapRowToTshirt);
    }

    @Override
    public void updateTshirt(Tshirt tshirt) {
        jdbcTemplate.update(UPDATE_TSHIRT_SQL,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity(),
                tshirt.getTshirtId());
    }

    @Override
    public void deleteTshirt(int id) {
        jdbcTemplate.update(DELETE_TSHIRT_SQL, id);
    }

    @Override
    public List<Tshirt> searchTshirtsByColor(String color) {
        return jdbcTemplate.query(SEARCH_TSHIRT_BY_COLOR, this::mapRowToTshirt, color);
    }

    @Override
    public List<Tshirt> searchTshirtsBySize(String size) {
        return jdbcTemplate.query(SEARCH_TSHIRT_BY_SIZE, this::mapRowToTshirt, size);
    }
    @Override
    public int getInventoryQuantity(int id) {
        return jdbcTemplate.queryForObject(SELECT_QUANTITY_FROM_TSHIRT_SQL, Integer.class, id);
    }
}
