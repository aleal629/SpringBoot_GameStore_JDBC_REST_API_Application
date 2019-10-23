package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Purchase {

    @Size(min = 1, max = 80, message = "Name entered is too long too fit in our database. Please use a shortened name.")
    private String name;
    @Size(min = 1, max = 30, message = "Street entered is too long to fit in our database. Please use an abbreviated name.")
    private String street;
    @Size(min = 1, max = 30, message = "City entered is too long to fit in our database. Please use an abbreviated name.")
    private String city;
    @Size(min = 1, max = 30, message = "State entered is too long to fit in our database. Please use valid US State name.")
    private String state;
    @Size(min = 1, max = 5, message = "Zip code entered is too long. Please use valid zip code.")
    private String zipCode;
    @Size(min = 1, max = 20, message = "Item type entered is too long to fit in our database. Please use a valid item type :" +
            " T-Shirts, Consoles, or Games.")
    private String itemType;
    @Digits(integer = 11, fraction = 0)
    private int itemId;
    @Digits(integer = 11, fraction = 0)
    @Min(value = 1, message = "Order quantity must be at least 1.")
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
        Purchase purchase = (Purchase) o;
        return getItemId() == purchase.getItemId() &&
                getQuantity() == purchase.getQuantity() &&
                getName().equals(purchase.getName()) &&
                getStreet().equals(purchase.getStreet()) &&
                getCity().equals(purchase.getCity()) &&
                getState().equals(purchase.getState()) &&
                getZipCode().equals(purchase.getZipCode()) &&
                getItemType().equals(purchase.getItemType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStreet(), getCity(), getState(), getZipCode(), getItemType(), getItemId(), getQuantity());
    }
}
