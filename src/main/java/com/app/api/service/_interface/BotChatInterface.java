package com.app.api.service._interface;

import com.app.api.model.BotChatModel;

import java.util.List;

public interface BotChatInterface {
    public List<BotChatModel> listChat();
    public boolean addChat(String Question,String Answer);
    public boolean updateChat(int idChat,String KeyQuestionOrAnswer,String QuestionOrAnswer);
    public boolean deleteChat(int idChat,String KeyQuestionOrAnswer);
}
