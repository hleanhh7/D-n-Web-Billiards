package com.anhtranbilliards.trananh_billiards.repository;

import com.anhtranbilliards.trananh_billiards.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { // (2)
    // Không cần viết code bên trong!
}
