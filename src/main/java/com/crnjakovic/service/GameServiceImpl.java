package com.crnjakovic.service;

import com.crnjakovic.DAO.GameRepository;
import com.crnjakovic.DAO.PlayerRepository;
import com.crnjakovic.model.Game;
import com.crnjakovic.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Component("gameService")
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean createGame(String userName) {
        if(!checkIfUserPlaying(userName)){
            gameRepository.saveAndFlush(new Game(playerRepository.findByUsername(userName), userName+"'s game"));
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> tmp;
        List<Game> validList = new ArrayList<>();
        tmp = gameRepository.findAll();
        for(Game g: tmp){
            if(g.getSecondPlayer() != null){
                validList.add(g);
            }
            else{
                g.setSecondPlayer(new Player("","","",""));
                validList.add(g);
            }
        }
        return validList;
    }

    @Override
    public boolean joinGame(Long gameId, String userName) {
        Game joinedGame = gameRepository.findById(gameId).get();
        Player playerTwo = playerRepository.findByUsername(userName);
        Player secondPlayer;
        if(joinedGame.getSecondPlayer()!=null){
            secondPlayer = joinedGame.getSecondPlayer();
        }
        else{
            secondPlayer = new Player();
        }
        if(joinedGame.getSecondPlayer()==null && !checkIfUserPlaying(userName)){
            joinedGame.setSecondPlayer(playerTwo);
            gameRepository.saveAndFlush(joinedGame);
            return true;
        }
        else if(joinedGame.getFirstPlayer().equals(playerTwo) || secondPlayer.equals(playerTwo)){
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public Game getGameForUser(Player user) {
        Game joinedGame = gameRepository.findGameByUser(user);
        return joinedGame;
    }

    @Override
    public boolean checkIfUserPlaying(String userName) {
        List<Game> tmp = gameRepository.findAll();
        boolean exists = false;
        String secondUser = "";
        for(Game g: tmp){
            if(g.getSecondPlayer()!=null){secondUser=g.getSecondPlayer().getUserName();}
            if(g.getFirstPlayer().getUserName().equals(userName) || secondUser.equals(userName)){
                exists = true;
            }
        }
        return exists;
    }
}
