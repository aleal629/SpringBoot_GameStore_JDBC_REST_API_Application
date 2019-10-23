package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoTest {

    @Autowired
    protected GameDao gameDao;

    @Before
    @After
    public void setUp(){
        //Cleans up test DB
        List<Game> games = gameDao.getAllGames();

        games.stream()
                .forEach(game -> gameDao.deleteGame(game.getGameId()));
    }

    @Test
    public void addGetDeleteGame() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        List<Game> games = gameDao.getAllGames();

        //Test get all Games and Add from helper method
        assertEquals(3, games.size());

        //Test get Game
        Game game = gameDao.getGame(games.get(0).getGameId());
        assertEquals(games.get(0), game);

        //Test Delete
        gameDao.deleteGame(games.get(0).getGameId());

        assertEquals(2, gameDao.getAllGames().size());

    }

    @Test
    public void getAllGames() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        List<Game> games = gameDao.getAllGames();

        //Test get all Games and Add from helper method
        assertEquals(3, games.size());
    }

    @Test
    public void updateGame() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        List<Game> games = gameDao.getAllGames();

        //Creates new game and updates the fields
        Game game = games.get(0);
        game.setTitle("Doom");
        game.setEsrbRating("M");
        game.setDescription("A 1993 first-person shooter (FPS) game developed by id Software for MS-DOS. It is one" +
                " of the most significant games in video game history, and is frequently cited as one of the " +
                "greatest games of all time.");
        game.setPrice(49.99);
        game.setStudio("id Software");
        game.setQuantity(666);

        gameDao.updateGame(game);

        //Test update worked
        assertEquals(game, gameDao.getGame(game.getGameId()));
    }

    @Test
    public void searchGameByStudio() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        //Tests search by Studio
        assertEquals(2, gameDao.searchGameByStudio("Nintendo").size());
    }

    @Test
    public void searchGameByRating() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        //Tests search by ESRB rating
        assertEquals(1, gameDao.searchGameByRating("T").size());
        assertEquals(2, gameDao.searchGameByRating("E").size());
    }

    @Test
    public void searchGameByTitle() {
        //Adds three games to database - see helper method
        helperGameDataLoad();
        //Test search by title
        assertEquals(1, gameDao.searchGameByTitle("The Legend of Zelda").size());
        assertEquals(1, gameDao.searchGameByTitle("Castlevania").size());
    }

    @Test
    public void getInventoryQuantity() {
        //Adds three games to database - see helper method
        helperGameDataLoad();

        List<Game> games = gameDao.getAllGames();

        //Tests get of inventory quantity
        assertEquals(100, gameDao.getInventoryQuantity(games.get(0).getGameId()));
        assertEquals(250, gameDao.getInventoryQuantity(games.get(1).getGameId()));
        assertEquals(350, gameDao.getInventoryQuantity(games.get(2).getGameId()));
    }

    private void helperGameDataLoad(){
        //Adds three games to database
        Game game = new Game();
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);
        game = gameDao.addGame(game);

        Game game1 = new Game();
        game1.setTitle("Castlevania");
        game1.setEsrbRating("T");
        game1.setDescription("An action-platformer video game developed and published by Konami for " +
                "the Family Computer Disk System video game console in Japan in September 1986");
        game1.setPrice(27.97);
        game1.setStudio("Konami");
        game1.setQuantity(250);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setTitle("Super Mario Bros.");
        game2.setEsrbRating("E");
        game2.setDescription("A platform video game developed and published by Nintendo. The successor to the 1983 " +
                "arcade game, Mario Bros., it was released in Japan in 1985 for the Famicom.");
        game2.setPrice(13.99);
        game2.setStudio("Nintendo");
        game2.setQuantity(350);
        game2 = gameDao.addGame(game2);

    }
}