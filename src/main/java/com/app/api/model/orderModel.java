package com.app.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="orders")
public class orderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="status")
    private int status;

    @Column(name="created_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp created_at;
    @Column(name="receive_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp receive_at;

    public Integer getId_Shipper() {
        return id_Shipper;
    }

    public void setId_Shipper(Integer id_Shipper) {
        this.id_Shipper = id_Shipper;
    }

    @Column(name="id_Shipper")
    private Integer id_Shipper = 0;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        receive_at = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "id_account",nullable = false)
    private accountModel accountModel;

    @ManyToOne
    @JoinColumn(name="id_store",nullable = false)
    private storeModel storeModel;

    @OneToOne(mappedBy = "orderModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private orderItemModel orderItemModel;

    public orderModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getReceive_at() {
        return receive_at;
    }

    public void setReceive_at(Timestamp receive_at) {
        this.receive_at = receive_at;
    }

    public com.app.api.model.accountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(com.app.api.model.accountModel accountModel) {
        this.accountModel = accountModel;
    }

    public com.app.api.model.storeModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(com.app.api.model.storeModel storeModel) {
        this.storeModel = storeModel;
    }

    public com.app.api.model.orderItemModel getOrderItemModel() {
        return orderItemModel;
    }

    public void setOrderItemModel(com.app.api.model.orderItemModel orderItemModel) {
        this.orderItemModel = orderItemModel;
    }
}
