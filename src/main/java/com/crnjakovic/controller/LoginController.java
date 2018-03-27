package com.crnjakovic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lukacrnjakovic on 3/24/18.
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage(){
        return "login.html";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register.html";
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("username", name);
        return "index.html";
    }
}
