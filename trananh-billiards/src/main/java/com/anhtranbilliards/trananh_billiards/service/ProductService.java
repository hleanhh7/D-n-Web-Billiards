package com.anhtranbilliards.trananh_billiards.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.anhtranbilliards.trananh_billiards.entity.*;
import com.anhtranbilliards.trananh_billiards.repository.*;

@Service 
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    } 

    public List<Product> getProducts(Long categoryId){
        if(categoryId == null){
            return productRepository.findByActiveTrue();
        }

        return productRepository.findByCategory_IdAndActiveTrue(categoryId);

    }

    public Product getProductById(Long id){
        return productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                "Not found has id" + id
            ));
    }
}


