package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Tshirt {


    @Digits(integer = 11, fraction = 0)
    private int tshirtId;
    @Size(min = 1, max = 20, message = "Size entered is too long to fit in our database. Please shorten to a maximum " +
            "of 20 characters.")
    @NotNull(message = "Size field cannot be null or empty. Please provide a valid value.")
    private String size;
    @Size(min = 1, max = 20, message = "Color entered is too long to fit in our database. Please shorten to a maximum " +
            "of 20 characters.")
    @NotNull(message = "Color field cannot be null or empty. Please provide a valid value.")
    private String color;
    @Size(min = 1, max = 255, message = "Description entered is too long to fit in our database. Please shorten to a maximum " +
            "of 255 characters.")
    @NotNull(message = "Description field cannot be null or empty. Please provide a valid value.")
    private String description;
    @Digits(integer = 3, fraction = 2, message = "Price for games cannot exceed more than 3 digits with two decimal places.")
    @NotNull(message = "Price field cannot be null or empty. Please provide a valid value.")
    private double price;
    @Digits(integer = 11, fraction = 0, message = "Quantity for games cannot exceed more than 99,999,999,999.")
    @NotNull(message = "Quantity field cannot be null or empty. Please provide a valid value.")
    private int quantity;

    public int getTshirtId() {
        return tshirtId;
    }

    public void setTshirtId(int tshirtId) {
        this.tshirtId = tshirtId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tshirt tshirt = (Tshirt) o;
        return getTshirtId() == tshirt.getTshirtId() &&
                Double.compare(tshirt.getPrice(), getPrice()) == 0 &&
                getQuantity() == tshirt.getQuantity() &&
                getSize().equals(tshirt.getSize()) &&
                getColor().equals(tshirt.getColor()) &&
                getDescription().equals(tshirt.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTshirtId(), getSize(), getColor(), getDescription(), getPrice(), getQuantity());
    }
}
