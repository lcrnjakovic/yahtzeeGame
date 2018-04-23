package com.crnjakovic.service;

import com.crnjakovic.DAO.PlayerRepository;
import com.crnjakovic.controller.UserDto;
import com.crnjakovic.model.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PlayerServiceImpl(PlayerRepository playerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Player findPlayerByUsername(String userName) {
        return playerRepository.findByUsername(userName);
    }

    @Override
    public Player registerNewUserAcount(UserDto userDto) {
        Player p = new Player();
        p.setUserName(userDto.getUserName());
        p.setFullName(userDto.getFullName());
        p.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        p.setRole("ROLE_USER");
        return playerRepository.saveAndFlush(p);
    }

}
