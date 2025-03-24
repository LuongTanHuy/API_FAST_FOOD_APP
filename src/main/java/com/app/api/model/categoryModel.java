package com.app.api.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="category")
public class categoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="category")
    private String category;
    @Column(name="sale")
    private int sale;
    @Column(name="status")
    private int status ;
    @OneToMany(mappedBy="categoryModel")
    private Set<productModel> products;
    @ManyToOne
    @JoinColumn(name="id_store", nullable=false)
    private storeModel storeModel;

    public categoryModel() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public com.app.api.model.storeModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(com.app.api.model.storeModel storeModel) {
        this.storeModel = storeModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public Set<productModel> getProducts() {
        return products;
    }

    public void setProducts(Set<productModel> products) {
        this.products = products;
    }
}
