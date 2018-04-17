package com.crnjakovic.config;

import com.crnjakovic.DAO.IPlayerInfoDAO;
import com.crnjakovic.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
@Service
public class YahtzeeUserDetailsService implements UserDetailsService {
    @Autowired
    private IPlayerInfoDAO playerInfoDAO;
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        Player activeUserInfo = playerInfoDAO.getActiveUser(userName);
        GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
        UserDetails userDetails = (UserDetails)new User(activeUserInfo.getUserName(),
                activeUserInfo.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}