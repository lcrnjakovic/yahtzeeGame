package com.crnjakovic.controller;

import com.crnjakovic.model.ChatMessage;
import com.crnjakovic.model.Score;
import com.crnjakovic.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * Created by lukacrnjakovic on 3/24/18.
 */
@Controller
public class ChatController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if(chatMessage.getType() == ChatMessage.MessageType.SCORE){
            String[] content = chatMessage.getContent().split(",");
            gameService.recordScore(content[0], Integer.parseInt(content[1]), chatMessage.getSender());
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
