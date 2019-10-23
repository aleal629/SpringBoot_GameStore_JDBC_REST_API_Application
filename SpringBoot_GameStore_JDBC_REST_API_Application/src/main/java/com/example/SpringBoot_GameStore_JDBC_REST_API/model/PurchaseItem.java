package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import java.util.Objects;

public class PurchaseItem {

    private String itemType;
    private String name;
    private String itemInformation;
    private double unitPrice;
    private int inventoryAmount;

    public PurchaseItem(){};

    public PurchaseItem(Game game) {
        itemType = "Game";
        name = game.getTitle();
        itemInformation = "Description: " + game.getDescription() +
                " ESRB Rating: " + game.getEsrbRating() + "." +
                " Studio: " + game.getStudio() + ".";
        unitPrice = game.getPrice();
        inventoryAmount = game.getQuantity();
    }

    public PurchaseItem(Console console) {
        itemType = "Console";
        name = console.getModel();
        itemInformation = "Manufacturer: " + console.getManufacturer() + "." +
                " Processor: " + console.getProcessor() + "." +
                " Memory: " + console.getMemoryAmount() + ".";
        unitPrice = console.getPrice();
        inventoryAmount = console.getQuantity();
    }

    public PurchaseItem(Tshirt tshirt) {
        itemType = "T-Shirt";
        name = tshirt.getColor() + " t-shirt.";
        itemInformation = "Description: " + tshirt.getDescription() + "." +
                " Size: " + tshirt.getSize() + ".";
        unitPrice = tshirt.getPrice();
        inventoryAmount = tshirt.getQuantity();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemInformation() {
        return itemInformation;
    }

    public void setItemInformation(String itemInformation) {
        this.itemInformation = itemInformation;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(int inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseItem that = (PurchaseItem) o;
        return Double.compare(that.getUnitPrice(), getUnitPrice()) == 0 &&
                getInventoryAmount() == that.getInventoryAmount() &&
                getItemType().equals(that.getItemType()) &&
                getName().equals(that.getName()) &&
                getItemInformation().equals(that.getItemInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemType(), getName(), getItemInformation(), getUnitPrice(), getInventoryAmount());
    }
}
