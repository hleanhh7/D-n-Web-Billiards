package com.anhtranbilliards.trananh_billiards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")

public class Product {

    // Cứ mỗi trước khi khai báo biến phải khai báo trong DB nó có dạng gì
    @Id // khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tăng tự động
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity = 0;

    protected Product() {
        // JPA cần constructor không tham số
    }

    // set và get cho id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // set và get cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // set và get chp price
    public Long getPrice() {
        return price; // Đã sửa lại trả về Long cho khớp
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    // getter and setter about category
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // set và get cho active
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // set và get cho tồn kho
    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
