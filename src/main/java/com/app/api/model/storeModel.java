package com.app.api.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "store")
public class storeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    private Timestamp created_at;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    @OneToMany(mappedBy = "storeModel")
    private Set<accountModel> accountModels;
    @OneToMany(mappedBy = "storeModel")
    private Set<orderModel> orderModels;

    @OneToMany(mappedBy = "storeModel")
    private Set<categoryModel> categoryModels;

    @OneToMany(mappedBy = "storeModel")
    private Set<chatModel> chatModel;

    public List<String> listCategory;
    public int totalOrdersSold;
    public double revenue;

    public storeModel() {
    }

    public void setTotalOrdersSold(int totalOrdersSold) {
        this.totalOrdersSold = totalOrdersSold;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public  void setListCategory(List<String> listCategory) {
        this.listCategory = listCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
