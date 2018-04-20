package com.crnjakovic.service;

import com.crnjakovic.model.Game;
import com.crnjakovic.model.Player;

import java.util.List;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
public interface GameService {
    boolean createGame(String userName);
    List<Game> getAllGames();
    boolean joinGame(Long gameId, String userName);
    Game getGameForUser(Player user);
    boolean checkIfUserPlaying(String userName);
    void recordScore(String combination, int result, String userName);
}
