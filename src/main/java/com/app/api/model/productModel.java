package com.app.api.model;

import com.app.api.service.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="product")
public class productModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="image")
    private String image;

    @Column(name="price")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private Double price;

    @Column(name="name")
    private String name;
    @Column(name="status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name="id_category", nullable=false)
    private categoryModel categoryModel;

    @OneToMany(mappedBy = "productModel")
    private Set<orderItemModel> orderItemModels;

    @OneToMany(mappedBy = "productModel")
    private Set<commentModel> commentModels;

    public int totalProductSold;
    public double totalRevenue;

    public productModel() {
    }

    public void setTotalProductSold(int totalProductSold) {
        this.totalProductSold = totalProductSold;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public categoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(categoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
