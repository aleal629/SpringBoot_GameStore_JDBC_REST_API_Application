package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Game {

    @Digits(integer = 11, fraction = 0)
    private int gameId;
    @Size(min = 1, max = 50, message = "Title name entered is too long to fit in our database. Please shorten to a maximum " +
            "of 50 characters.")
    @NotNull(message = "Game Title field cannot be null or empty. Please provide a valid value.")
    private String title;
    @Size(min = 1, max = 50, message = "ESRB rating entered is too long to fit in our database. Please shorten to a maximum " +
            "of 50 characters.")
    @NotNull(message = "ESRB Rating field cannot be null or empty. Please provide a valid value.")
    private String esrbRating;
    @Size(min = 1, max = 255, message = "Description entered is too long to fit in our database. Please shorten to a maximum " +
            "of 255 characters.")
    @NotNull(message = "Description field cannot be null or empty. Please provide a valid value.")
    private String description;
    @Digits(integer = 3, fraction = 2, message = "Price for games cannot exceed more than 3 digits with two decimal places.")
    @NotNull(message = "Price field cannot be null or empty. Please provide a valid value.")
    private double price;
    @Size(min = 1, max = 50, message = "Studio name entered is too long to fit in our database. Please shorten to a maximum " +
            "of 50 characters.")
    @NotNull(message = "Studio field cannot be null or empty. Please provide a valid value.")
    private String studio;
    @Digits(integer = 11, fraction = 0)
    @NotNull(message = "Quantity field cannot be null or empty. Please provide a valid value.")
    private int quantity;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
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

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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
        Game game = (Game) o;
        return getGameId() == game.getGameId() &&
                Double.compare(game.getPrice(), getPrice()) == 0 &&
                getQuantity() == game.getQuantity() &&
                getTitle().equals(game.getTitle()) &&
                getEsrbRating().equals(game.getEsrbRating()) &&
                getDescription().equals(game.getDescription()) &&
                getStudio().equals(game.getStudio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getTitle(), getEsrbRating(), getDescription(), getPrice(), getStudio(), getQuantity());
    }
}
