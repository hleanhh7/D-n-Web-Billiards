package com.anhtranbilliards.trananh_billiards.repository;

import com.anhtranbilliards.trananh_billiards.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.classfile.ClassFile.Option;
//import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


//@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { // (2)
    // Không cần viết code bên trong!
    //List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByActiveTrue(); //đang hiển thị

    List<Product> findByCategory_IdAndActiveTrue(Long categoryId); //categoryid đã chọn và đang hiển thị

    Optional<Product> findByIdAndActiveTrue(Long id);//id đã chọn và đang hiển thị
}
