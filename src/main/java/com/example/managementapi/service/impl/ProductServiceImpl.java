package com.example.managementapi.service.impl;

import com.example.managementapi.config.JwtFilter;
import com.example.managementapi.constants.ManagementConstants;
import com.example.managementapi.dto.ProductDTO;
import com.example.managementapi.model.Category;
import com.example.managementapi.model.Product;
import com.example.managementapi.repository.ProductDao;
import com.example.managementapi.service.ProductService;
import com.example.managementapi.utils.ManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductDao productDao;


    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
                if(jwtFilter.isAdmin()){
                        if(validateProductMap(requestMap, false)){
                            productDao.save(getProductFromMap(requestMap, false));
                            return ManagementUtils.getResponseEntity("Product Added Successfully." ,HttpStatus.OK);
                        }
                        return ManagementUtils.getResponseEntity(ManagementConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                } else
                    return ManagementUtils.getResponseEntity(ManagementConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")  ){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        try{
        return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
       try{
            if(jwtFilter.isAdmin()){
                    if(validateProductMap(requestMap, true)){
                       Optional<Product> optional =  productDao.findById(Integer.parseInt(requestMap.get("id")));
                       if(!optional.isEmpty()){
                            Product product = getProductFromMap(requestMap, true);
                            product.setStatus(optional.get().getStatus());
                            productDao.save(product);
                            return  ManagementUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                       }else{
                           return  ManagementUtils.getResponseEntity("Product is does not exits.", HttpStatus.OK);
                       }
                    }else{
                        return ManagementUtils.getResponseEntity(ManagementConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
                    }
            }else{
                return ManagementUtils.getResponseEntity(ManagementConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
       }catch(Exception ex){
           ex.printStackTrace();
       }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
