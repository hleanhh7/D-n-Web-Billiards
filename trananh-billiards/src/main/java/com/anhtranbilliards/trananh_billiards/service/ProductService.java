package com.anhtranbilliards.trananh_billiards.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anhtranbilliards.trananh_billiards.entity.*;
import com.anhtranbilliards.trananh_billiards.repository.*;

@Service 
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    } 

    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
