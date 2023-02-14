package iss.nus.workshop27_class.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import iss.nus.workshop27_class.model.Game;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@Repository
public class GamesSqlRepository {
    
    private final static String SQL_GET_ALL_GAMES = "SELECT gid, name FROM game LIMIT ? OFFSET ?;";
    private final static String SQL_GET_GAME_BY_ID = "SELECT * FROM game WHERE gid = ? LIMIT 1";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Game> getGames(Integer limit, Integer offset) {

        List<Game> games = new LinkedList<>();
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_GAMES, limit, offset);

        while (rs.next()) {
            games.add(Game.fromSql(rs));
        }

        return games;
    }


    public Optional<Game> getGameById(Integer id) {
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_GAME_BY_ID, id);

        Game game = rs.next() ? Game.fromSql(rs) : null;

        return Optional.ofNullable(game);
    }

}
