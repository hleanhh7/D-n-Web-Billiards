package com.anhtranbilliards.trananh_billiards.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.anhtranbilliards.trananh_billiards.entity.Product;
import com.anhtranbilliards.trananh_billiards.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<Product> listProducts(
        @RequestParam(name = "categoryId", required = false) Long categoryId){
            return productService.getProducts(categoryId);
    }

    @GetMapping("/api/products/{id}")
    public Product getProductsById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }
    
}
