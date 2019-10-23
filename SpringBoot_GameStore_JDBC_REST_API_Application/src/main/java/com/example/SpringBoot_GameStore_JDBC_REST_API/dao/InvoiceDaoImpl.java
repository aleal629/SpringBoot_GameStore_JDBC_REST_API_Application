package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.InvalidItemTypeException;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Invoice;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {

    private final String INSERT_INVOICE_SQL =
            "insert into invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal," +
                    " tax, processing_fee, total) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String SELECT_INVOICE_SQL =
            "select * from invoice where invoice_id = ?";

    private final String SELECT_ALL_INVOICES_SQL =
            "select * from invoice";

    private final String UPDATE_INVOICE_SQL =
            "update invoice set name = ?, street = ?, city = ?, state = ?, zipcode = ?, item_type = ?, item_id = ?, " +
                    "unit_price = ?, quantity = ?, subtotal = ?, tax = ?, processing_fee = ?, total = ? " +
                    "where invoice_id = ?";

    private final String DELETE_INVOICE_SQL =
            "delete from invoice where invoice_id = ?";

    private final String SELECT_UNIT_PRICE_FROM_GAMES_SQL =
            "select price from game where game_id = ?";

    private final String SELECT_UNIT_PRICE_FROM_CONSOLES_SQL =
            "select price from console where console_id = ?";

    private final String SELECT_UNIT_PRICE_FROM_TSHIRTS_SQL =
            "select price from t_shirt where t_shirt_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setName(rs.getString("name"));
        invoice.setStreet(rs.getString("street"));
        invoice.setCity(rs.getString("city"));
        invoice.setState(rs.getString("state"));
        invoice.setZipCode(rs.getString("zipcode"));
        invoice.setItemType(rs.getString("item_type"));
        invoice.setItemId(rs.getInt("item_id"));
        invoice.setUnitPrice(rs.getDouble("unit_price"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setSubtotal(rs.getDouble("subtotal"));
        invoice.setTax(rs.getDouble("tax"));
        invoice.setProcessingFee(rs.getDouble("processing_fee"));
        invoice.setTotal(rs.getDouble("total"));

        return invoice;
    }

    @Override
    @Transactional
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipCode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    @Override
    public Invoice getInvoice(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_SQL, this::mapRowToInvoice, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public double getUnitPrice(Purchase purchase) {
        if (purchase.getItemType().equalsIgnoreCase("consoles")) {
            return jdbcTemplate.queryForObject(SELECT_UNIT_PRICE_FROM_CONSOLES_SQL, Double.class, purchase.getItemId());
        } else if (purchase.getItemType().equalsIgnoreCase("games")) {
            return jdbcTemplate.queryForObject(SELECT_UNIT_PRICE_FROM_GAMES_SQL, Double.class, purchase.getItemId());
        } else if (purchase.getItemType().equalsIgnoreCase("t-shirts")) {
            return jdbcTemplate.queryForObject(SELECT_UNIT_PRICE_FROM_TSHIRTS_SQL, Double.class, purchase.getItemId());
        } else
            throw new InvalidItemTypeException("Invalid item type has been entered.");
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES_SQL, this::mapRowToInvoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(UPDATE_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipCode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal(),
                invoice.getInvoiceId());
    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, id);
    }
}
