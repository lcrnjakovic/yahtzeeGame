package com.crnjakovic.service;

import com.crnjakovic.DAO.GameRepository;
import com.crnjakovic.DAO.PlayerRepository;
import com.crnjakovic.DAO.ScoreRepository;
import com.crnjakovic.model.Game;
import com.crnjakovic.model.Player;
import com.crnjakovic.model.Score;
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
    private final ScoreRepository scoreRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, ScoreRepository scoreRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
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

    @Override
    public void recordScore(String combination, int result, String userName) {
        Player currentPlayer = playerRepository.findByUsername(userName);
        Game ongoingGame = gameRepository.findGameByUser(currentPlayer);
        Score score;
        if(ongoingGame.getFirstPlayer().getUserName().equals(currentPlayer.getUserName())){
            if(ongoingGame.getPlayerOneScore()!=null){
                score = scoreRepository.findById(ongoingGame.getPlayerOneScore().getId()).get();
            }
            else{
                score = new Score();
            }
        }
        else{
            if(ongoingGame.getPlayerTwoScore()!=null){
                score = scoreRepository.findById(ongoingGame.getPlayerTwoScore().getId()).get();
            }
            else{
                score = new Score();
            }
        }
        switch (combination){
            case "aces": score.setAces(result);
                break;
            case "twos": score.setTwos(result);
                break;
            case "threes": score.setThrees(result);
                break;
            case "fours": score.setFours(result);
                break;
            case "fives": score.setFives(result);
                break;
            case "sixes": score.setSixes(result);
                break;
            case "threekind": score.setThreeKind(result);
                break;
            case "fourkind": score.setFourKind(result);
                break;
            case "full": score.setFull(result);
                break;
            case "small": score.setSmall(result);
                break;
            case "large": score.setLarge(result);
                break;
            case "chance": score.setChance(result);
                break;
            case "yahtzee": score.setYahtzee(result);
                break;
        }
        if(ongoingGame.getFirstPlayer().getUserName().equals(currentPlayer.getUserName())) {
            ongoingGame.setPlayerOneScore(score);
        }
        else{
            ongoingGame.setPlayerTwoScore(score);
        }
        scoreRepository.saveAndFlush(score);
        gameRepository.saveAndFlush(ongoingGame);
    }

    @Override
    public void deleteGame(String player) {
        Game toDelete = gameRepository.findGameByUser(playerRepository.findByUsername(player));
        try{
            gameRepository.delete(toDelete);
        }
        catch (Exception e){
            System.out.println("Game already disbanded");
        }
    }
}
