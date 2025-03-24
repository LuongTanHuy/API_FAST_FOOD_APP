package com.app.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="comment")
public class commentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="comment")
    private String comment;

    @Column(name="created_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Timestamp created_at;

    @Column(name="image")
    private String image;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @Column(name="star")
    private int star;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }
    @ManyToOne
    @JoinColumn(name="id_account",nullable = false)
    private accountModel accountModel;

    @ManyToOne
    @JoinColumn(name="id_product",nullable = false)
    private productModel productModel;

    public commentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public com.app.api.model.accountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(com.app.api.model.accountModel accountModel) {
        this.accountModel = accountModel;
    }

    public com.app.api.model.productModel getProductModel() {
        return productModel;
    }

    public void setProductModel(com.app.api.model.productModel productModel) {
        this.productModel = productModel;
    }
}
