package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoImpl implements GameDao {

    private final String INSERT_GAME_SQL =
            "insert into game (title, esrb_rating, description, price, studio, quantity) " +
                    "values (?, ?, ?, ?, ?, ?)";

    private final String SELECT_GAME_SQL =
            "select * from game where game_id = ?";

    private final String SELECT_ALL_GAMES_SQL =
            "select * from game";

    private final String UPDATE_GAME_SQL =
            "update game set title = ?, esrb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? " +
                    "where game_id = ?";

    private final String DELETE_GAME_SQL =
            "delete from game where game_id = ?";

    private final String SEARCH_GAME_BY_STUDIO_SQL =
            "select * from game where studio = ?";

    private final String SEARCH_GAME_BY_RATING_SQL =
            "select * from game where esrb_rating = ?";

    private final String SEARCH_GAME_BY_TITLE_SQL =
            "select * from game where title LIKE ?";

    private final String SELECT_QUANTITY_FROM_GAME_SQL =
            "select quantity from game where game_id =?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoImpl(JdbcTemplate newJdbcTemplate){
        this.jdbcTemplate = newJdbcTemplate;
    }

    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setEsrbRating(rs.getString("esrb_rating"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getDouble("price"));
        game.setStudio(rs.getString("studio"));
        game.setQuantity(rs.getInt("quantity"));
        return game;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        jdbcTemplate.update(INSERT_GAME_SQL,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        game.setGameId(id);

        return game;
    }

    @Override
    public Game getGame(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_GAME_SQL, this::mapRowToGame, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Game> getAllGames() {
        return jdbcTemplate.query(SELECT_ALL_GAMES_SQL, this::mapRowToGame);
    }

    @Override
    public void updateGame(Game game) {
        jdbcTemplate.update(UPDATE_GAME_SQL,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity(),
                game.getGameId());
    }

    @Override
    public void deleteGame(int id) {
        jdbcTemplate.update(DELETE_GAME_SQL, id);
    }

    @Override
    public List<Game> searchGameByStudio(String studio) {
        return jdbcTemplate.query(SEARCH_GAME_BY_STUDIO_SQL, this::mapRowToGame, studio);
    }

    @Override
    public List<Game> searchGameByRating(String esrbRating) {
        return jdbcTemplate.query(SEARCH_GAME_BY_RATING_SQL, this::mapRowToGame, esrbRating);
    }

    @Override
    public List<Game> searchGameByTitle(String title) {
        title = "%" + title + "%";
        return jdbcTemplate.query(SEARCH_GAME_BY_TITLE_SQL, this::mapRowToGame, title);
    }

    @Override
    public int getInventoryQuantity(int id) {
        return jdbcTemplate.queryForObject(SELECT_QUANTITY_FROM_GAME_SQL, Integer.class, id);
    }
}
