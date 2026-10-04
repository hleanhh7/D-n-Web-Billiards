package com.anhtranbilliards.trananh_billiards.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anhtranbilliards.trananh_billiards.entity.Product;
import com.anhtranbilliards.trananh_billiards.service.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<Product> listProducts() {
        return productService.getProducts();
    }
}
