package com.example.demo.repository.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "products")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    
    @JsonProperty("product_id")
    @Column(length = 50, unique = true)
    private String productId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer cost;

    @Column(length = 50, nullable = false)
    private String category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}