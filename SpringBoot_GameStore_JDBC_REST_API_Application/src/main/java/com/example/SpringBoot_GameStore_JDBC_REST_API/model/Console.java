package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Console {

    @Digits(integer = 11, fraction = 0)
    private int consoleId;
    @Size(min = 1, max = 50, message = "Model name field entered is too long to fit in our database. Please shorten to a maximum " +
            "of 50 characters.")
    @NotNull(message = "Model cannot be null or empty. Please provide a valid value.")
    private String model;
    @Size(min = 1, max = 50, message = "Manufacturer name entered is too long to fit in our database. Please shorten to a maximum " +
            "of 50 characters.")
    @NotNull(message = "Manufacturer field cannot be null or empty. Please provide a valid value.")
    private String manufacturer;
    @Size(min = 1, max = 20, message = "Memory amount entered is too long to fit in our database. Please shorten to a maximum " +
            "of 20 characters.")
    @NotNull(message = "Memory Amount field cannot be null or empty. Please provide a valid value.")
    private String memoryAmount;
    @Size(min = 1, max = 20, message = "Processor information entered is too long to fit in our database. Please shorten to a maximum " +
            "of 20 characters.")
    @NotNull(message = "Processor field cannot be null or empty. Please provide a valid value.")
    private String processor;
    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "Price field cannot be null or empty. Please provide a valid value.")
    private double price;
    @Digits(integer = 11, fraction = 0)
    @NotNull(message = "Quantity field cannot be null or empty. Please provide a valid value.")
    private int quantity;

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemoryAmount() {
        return memoryAmount;
    }

    public void setMemoryAmount(String memoryAmount) {
        this.memoryAmount = memoryAmount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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
        Console console = (Console) o;
        return getConsoleId() == console.getConsoleId() &&
                Double.compare(console.getPrice(), getPrice()) == 0 &&
                getQuantity() == console.getQuantity() &&
                getModel().equals(console.getModel()) &&
                getManufacturer().equals(console.getManufacturer()) &&
                getMemoryAmount().equals(console.getMemoryAmount()) &&
                getProcessor().equals(console.getProcessor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsoleId(), getModel(), getManufacturer(), getMemoryAmount(), getProcessor(), getPrice(), getQuantity());
    }
}
