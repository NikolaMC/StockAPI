package com.example.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetailsRequestModel {
	@JsonProperty("product_id")
    private String productId;
    String name, category;
    private Integer cost;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

}