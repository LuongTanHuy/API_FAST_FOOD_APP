package com.app.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="chat")
public class chatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="created_at")
    @JsonFormat(pattern = "HH:mm:ss  dd-MM-yyyy ")
    private Timestamp created_at;

    @Column(name="message")
    private String message;

    @Column(name="typeMessage")
    private String typeMessage;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }
    @ManyToOne
    @JoinColumn(name="id_account",nullable = false)
    private accountModel accountModel;

    @ManyToOne
    @JoinColumn(name="id_store",nullable = false)
    private storeModel storeModel;

    @Column(name="userSend")
    private String userSend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
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

    public accountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(accountModel accountModel) {
        this.accountModel = accountModel;
    }

    public storeModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(storeModel storeModel) {
        this.storeModel = storeModel;
    }

}
