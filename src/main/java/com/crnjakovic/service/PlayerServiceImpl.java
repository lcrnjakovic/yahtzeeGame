package com.crnjakovic.service;

import com.crnjakovic.DAO.PlayerRepository;
import com.crnjakovic.model.Player;
import org.springframework.stereotype.Component;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findPlayerByUsername(String userName) {
        return playerRepository.findByUsername(userName);
    }
}
