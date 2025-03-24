package com.app.api.controller.api;


import com.app.api.model.BotChatModel;
import com.app.api.service._interface.BotChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("apiBotChatController")
@RequestMapping("/api/v1/")
public class BotChatController {

    @Autowired
    private BotChatInterface botChatInterface;

    @GetMapping("chat-bot")
    public List<BotChatModel> listChat(){
        return this.botChatInterface.listChat();
    }

    @PostMapping("add-chat")
    public ResponseEntity<String> addChat(@RequestBody BotChatModel botChatModel){
        return  this.botChatInterface.addChat(botChatModel.getTrainer(), botChatModel.getModel())?
                ResponseEntity.status(200).body("Success"):
                ResponseEntity.status(500).body("fail");
    }

    @DeleteMapping("delete-chat")
    public ResponseEntity<String> deleteChat(@RequestParam("idChat") int idChat,
                                             @RequestParam("key") String KeyQuestionOrAnswer) {
        return  this.botChatInterface.deleteChat(idChat, KeyQuestionOrAnswer)?
                ResponseEntity.status(200).body("Success"):
                ResponseEntity.status(500).body("Fail");
    }

    @PutMapping("update-chat")
    public ResponseEntity<String> updateChat(@RequestParam("id") int id,
                                             @RequestParam("key") String KeyQuestionOrAnswer,
                                             @RequestParam("text") String QuestionOrAnswer) {
        return this.botChatInterface.updateChat(id, KeyQuestionOrAnswer,QuestionOrAnswer)?
                ResponseEntity.status(200).body("Success"):
                ResponseEntity.status(500).body("Fail");
    }

}
