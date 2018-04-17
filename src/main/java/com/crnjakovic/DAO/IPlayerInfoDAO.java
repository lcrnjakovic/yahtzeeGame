package com.crnjakovic.DAO;

import com.crnjakovic.model.Player;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
public interface IPlayerInfoDAO {
    Player getActiveUser(String userName);
}
