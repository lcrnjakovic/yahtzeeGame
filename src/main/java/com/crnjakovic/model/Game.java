package com.crnjakovic.model;

import javax.persistence.*;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Entity
@Table(name = "game")
public class Game {
    public Game() {
    }

    public Game(Player firstPlayer, String gameName) {
        this.firstPlayer = firstPlayer;
        this.gameName = gameName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="game_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = true)
    private Player secondPlayer;
    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false)
    private Player firstPlayer;
    @Column(name="game_name")
    private String gameName;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="first_player_score", nullable = true)
    private Score playerOneScore;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="second_player_score", nullable = true)
    private Score playerTwoScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Score getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(Score playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public Score getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(Score playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }
}
