package com.example.managementapi.service;

import com.example.managementapi.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {


    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductDTO>> getAllProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteUpdate(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProductDTO>> getByCategory(Integer id);

    ResponseEntity<ProductDTO> getProductById(Integer id);
}
