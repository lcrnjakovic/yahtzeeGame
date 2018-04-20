package com.crnjakovic.model;

import javax.persistence.*;

/**
 * Created by lukacrnjakovic on 4/20/18.
 */
@Entity
@Table(name = "scoreboard")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="score_id")
    private Long id;
    @Column(name="aces")
    private int aces;
    @Column(name="twos")
    private int twos;
    @Column(name="threes")
    private int threes;
    @Column(name="fours")
    private int fours;
    @Column(name="fives")
    private int fives;
    @Column(name="sixes")
    private int sixes;
    @Column(name="three_of_kind")
    private int threeKind;
    @Column(name="four_of_kind")
    private int fourKind;
    @Column(name="full_house")
    private int full;
    @Column(name="small")
    private int small;
    @Column(name="large")
    private int large;
    @Column(name="chance")
    private int chance;
    @Column(name="yahtzee")
    private int yahtzee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public int getTwos() {
        return twos;
    }

    public void setTwos(int twos) {
        this.twos = twos;
    }

    public int getThrees() {
        return threes;
    }

    public void setThrees(int threes) {
        this.threes = threes;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getFives() {
        return fives;
    }

    public void setFives(int fives) {
        this.fives = fives;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public int getThreeKind() {
        return threeKind;
    }

    public void setThreeKind(int threeKind) {
        this.threeKind = threeKind;
    }

    public int getFourKind() {
        return fourKind;
    }

    public void setFourKind(int fourKind) {
        this.fourKind = fourKind;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getYahtzee() {
        return yahtzee;
    }

    public void setYahtzee(int yahtzee) {
        this.yahtzee = yahtzee;
    }
}