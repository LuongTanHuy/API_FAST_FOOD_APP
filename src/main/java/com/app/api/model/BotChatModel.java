package com.app.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "botchat")
public class BotChatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="trainer")
    private String trainer ;
    @Column(name="model")
    private String model;

    public BotChatModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}