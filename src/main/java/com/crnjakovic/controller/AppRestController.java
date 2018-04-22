package com.crnjakovic.controller;

import com.crnjakovic.model.Game;
import com.crnjakovic.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lukacrnjakovic on 4/22/18.
 */
@RestController
@RequestMapping("app")
public class AppRestController {

    @Autowired
    private GameService gameService;

    @GetMapping("secure/api/games")
    public List<Game> listGames(){
        return gameService.getAllGames();
    }
}
