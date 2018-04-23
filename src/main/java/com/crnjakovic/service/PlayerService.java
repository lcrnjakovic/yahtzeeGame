package com.crnjakovic.service;

import com.crnjakovic.controller.UserDto;
import com.crnjakovic.model.Player;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
public interface PlayerService {
    Player findPlayerByUsername(String userName);
    Player registerNewUserAcount(UserDto userDto);
}
