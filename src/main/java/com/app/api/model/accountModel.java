package com.app.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Set;

import java.sql.Timestamp;

@Entity
@Table(name = "account")
public class accountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "accountModel")
    private Set<orderModel> orderModels;

    @OneToMany(mappedBy = "accountModel")
    private Set<commentModel> commentModels;

    @OneToMany(mappedBy = "accountModel")
    private Set<chatModel> chatModel;


    public storeModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(storeModel storeModel) {
        this.storeModel = storeModel;
    }

    @ManyToOne
    @JoinColumn(name = "id_store")
    private storeModel storeModel;

    @Column(name="status")
    private int status;
    @Column(name="oauth_provider")
    private String oauth_provider;
    @Column(name="oauth_uid")
    private String oauth_uid;
    @Column(name="image")
    private String image;
    @Column(name="username")
    private String username;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="phone")
    private String phone;
    @Column(name="address")
    private String address;
    @Column(name="created_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp created_at;
    @Column(name="updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp updated_at;
    @Column(name="otp")
    private String otp;

    @Column(name="permission")
    private String permission;

    private int totalOrderDelivered;
    private int totalOrderBought;
    public accountModel() {

    }

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Timestamp(System.currentTimeMillis());
    }


    public accountModel(int id, String image, int status, String oauth_provider, String oauth_uid, String username, String email, String password, String phone, String address, Timestamp created_at, Timestamp updated_at, String otp, String permission) {
        this.id = id;
        this.image = image;
        this.status = status;
        this.oauth_provider = oauth_provider;
        this.oauth_uid = oauth_uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.otp = otp;
        this.permission = permission;
    }

    public void setTotalOrderBought(int totalOrderBought) {
        this.totalOrderBought = totalOrderBought;
    }

    public void setTotalOrderDelivered(int totalOrderDelivered) {
        this.totalOrderDelivered = totalOrderDelivered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<orderModel> getOrderModels() {
        return orderModels;
    }

    public void setOrderModels(Set<orderModel> orderModels) {
        this.orderModels = orderModels;
    }

    public Set<commentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(Set<commentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public Set<com.app.api.model.chatModel> getChatModel() {
        return chatModel;
    }

    public void setChatModel(Set<com.app.api.model.chatModel> chatModel) {
        this.chatModel = chatModel;
    }

    public String getOauth_provider() {
        return oauth_provider;
    }

    public void setOauth_provider(String oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    public String getOauth_uid() {
        return oauth_uid;
    }

    public void setOauth_uid(String oauth_uid) {
        this.oauth_uid = oauth_uid;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public int getTotalOrderDelivered() {
        return totalOrderDelivered;
    }

    public int getTotalOrderBought() {
        return totalOrderBought;
    }
}