package com.example.managementapi.repository;

import com.example.managementapi.dto.ProductDTO;
import com.example.managementapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    List<ProductDTO> getAllProduct();


    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);


    List<ProductDTO> getProductByCategory(@Param("id") Integer id);

    ProductDTO getProductById(@Param("id") Integer id);
}
