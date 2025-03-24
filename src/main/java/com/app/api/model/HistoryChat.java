package com.app.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class HistoryChat {
    private String imageUser;
    private String imageStore;
    private String message;
    private String typeMessage;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    private Timestamp time;
    private String sender;
    private int idAccount;
    private int idStore;

    public HistoryChat() {
    }

    public HistoryChat(String imageUser, String imageStore, String message, String typeMessage, Timestamp time, String sender, int idAccount, int idStore) {
        this.imageUser = imageUser;
        this.imageStore = imageStore;
        this.message = message;
        this.typeMessage = typeMessage;
        this.time = time;
        this.sender = sender;
        this.idAccount = idAccount;
        this.idStore = idStore;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getImageStore() {
        return imageStore;
    }

    public void setImageStore(String imageStore) {
        this.imageStore = imageStore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }
}
