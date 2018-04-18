package com.crnjakovic.service;

import com.crnjakovic.model.Game;
import com.crnjakovic.model.Player;

import java.util.List;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
public interface GameService {
    void createGame(String userName);
    List<Game> getAllGames();
    void joinGame(Long gameId, String userName);
}
