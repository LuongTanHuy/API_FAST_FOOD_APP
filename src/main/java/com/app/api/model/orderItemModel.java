package com.app.api.model;

import jakarta.persistence.*;

@Entity
@Table(name="orderitem")
public class orderItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private productModel productModel;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_order",referencedColumnName = "id")
    private orderModel orderModel;

    public orderItemModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public com.app.api.model.productModel getProductModel() {
        return productModel;
    }

    public void setProductModel(com.app.api.model.productModel productModel) {
        this.productModel = productModel;
    }

    public com.app.api.model.orderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(com.app.api.model.orderModel orderModel) {
        this.orderModel = orderModel;
    }
}
