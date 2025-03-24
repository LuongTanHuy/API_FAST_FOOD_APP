package com.app.api.model;

public class Customer {
    private String image;
    private String username;
    private int idAccount;
    private int idStore;

    public Customer() {
    }

    public Customer(String image, String username, int idAccount, int idStore) {
        this.image = image;
        this.username = username;
        this.idAccount = idAccount;
        this.idStore = idStore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
