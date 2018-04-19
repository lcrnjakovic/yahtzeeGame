package com.crnjakovic.controller;

import com.crnjakovic.model.Game;
import com.crnjakovic.service.GameService;
import com.crnjakovic.service.GameServiceImpl;
import com.crnjakovic.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lukacrnjakovic on 3/24/18.
 */
@Controller
@RequestMapping("app")
public class AppController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
    @GetMapping("secure/game")
    public ModelAndView playGame(Model model) {
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Game joinedGame = gameService.getGameForUser(playerService.findPlayerByUsername(name));
        model.addAttribute("username", name);
        model.addAttribute("player1", joinedGame.getFirstPlayer().getUserName());
        if(joinedGame.getSecondPlayer()!=null){
            model.addAttribute("player2", joinedGame.getSecondPlayer().getUserName());
        }
        mav.setViewName("game");
        return mav;
    }

    @GetMapping("secure/home")
    public ModelAndView getGameList(Model model){
        ModelAndView mav = new ModelAndView();
        mav.addObject("games", gameService.getAllGames());
        mav.setViewName("gamelist");
        return mav;
    }

    @PostMapping("secure/new-game")
    public String createGame(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(gameService.createGame(name)){
            return "redirect:game";
        }
        else{
            return "redirect:home";
        }
    }

    @GetMapping("secure/join-game/{gameId}")
    public String joinGame(@PathVariable int gameId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(gameService.joinGame((long) gameId, name)){
            return "redirect:/app/secure/game";
        }
        else{
            return "redirect:/app/secure/home";
        }
    }

}

