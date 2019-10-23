package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Game;

import java.util.List;

public interface GameDao {

    Game addGame(Game game);

    Game getGame(int id);

    List<Game> getAllGames();

    void updateGame(Game game);

    void deleteGame(int id);

    List<Game> searchGameByStudio(String studio);

    List<Game> searchGameByRating(String esrbRating);

    List<Game> searchGameByTitle(String title);

    int getInventoryQuantity(int id);
}
