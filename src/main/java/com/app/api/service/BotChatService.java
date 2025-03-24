package com.app.api.service;

import com.app.api.model.BotChatModel;
import com.app.api.repository._interface.BotChatRepository;
import com.app.api.service._interface.BotChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BotChatService implements BotChatInterface {
    private final BotChatRepository botChatRepository;

    @Autowired
    public BotChatService(BotChatRepository botChatRepository) {
        this.botChatRepository = botChatRepository;
    }

    @Override
    public List<BotChatModel> listChat() {
        return this.botChatRepository.findAll().stream().sorted(Comparator.comparing(BotChatModel::getId)).toList();
    }

    @Override
    public boolean addChat(String Question, String Answer) {
        if(!Question.isEmpty() && !Answer.isEmpty()){
            BotChatModel newChat = new BotChatModel();
            newChat.setTrainer(Question);
            newChat.setModel(Answer);
            this.botChatRepository.save(newChat);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateChat(int idChat,String KeyQuestionOrAnswer,String QuestionOrAnswer) {
        Optional<BotChatModel> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChatModel updateChat = getChat.get();
            if (KeyQuestionOrAnswer.equals("question")) {
                updateChat.setTrainer(QuestionOrAnswer);
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            } else if (KeyQuestionOrAnswer.equals("answer")) {
                updateChat.setModel(QuestionOrAnswer);
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean checkNull(int idChat){
        Optional<BotChatModel> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChatModel updateChat = getChat.get();
            if(updateChat.getModel().equals("") && updateChat.getTrainer().equals("")){
                this.botChatRepository.deleteById(idChat);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteChat(int idChat,String KeyQuestionOrAnswer) {
        Optional<BotChatModel> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChatModel updateChat = getChat.get();
            if (KeyQuestionOrAnswer.equals("question")) {
                updateChat.setTrainer("");
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            } else if (KeyQuestionOrAnswer.equals("answer")) {
                updateChat.setModel("");
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            }

            return false;
        }
        return false;
    }

}
