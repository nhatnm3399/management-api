package com.example.managementapi.repository;

import com.example.managementapi.dto.ProductDTO;
import com.example.managementapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {


    List<ProductDTO> getAllProduct();
}
