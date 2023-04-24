package com.example.managementapi.controller.impl;


import com.example.managementapi.constants.ManagementConstants;
import com.example.managementapi.controller.ProductRest;
import com.example.managementapi.dto.ProductDTO;
import com.example.managementapi.service.ProductService;
import com.example.managementapi.utils.ManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {


    @Autowired
    ProductService productService;



    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        try{
            return productService.getAllProduct();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
       try{
        return productService.updateProduct(requestMap);
       }catch(Exception ex){
           ex.printStackTrace();
       }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
