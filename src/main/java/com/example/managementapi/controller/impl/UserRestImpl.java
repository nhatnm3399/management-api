//package com.example.managementapi.restImpl;
//
//import com.example.managementapi.constents.ManagementConstants;
//import com.example.managementapi.rest.UserRest;
//import com.example.managementapi.service.UserService;
//import com.example.managementapi.utils.ManagementUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//
//@RestController
//public class UserRestImpl implements UserRest {
//
//    @Autowired
//    UserService userService;
//
//
//    @Override
//    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
//        try {
//            return userService.signUp(requestMap);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
//
package com.example.managementapi.controller.impl;

import com.example.managementapi.constants.ManagementConstants;
import com.example.managementapi.controller.UserRest;
import com.example.managementapi.dto.UserDTO;
import com.example.managementapi.service.UserService;
import com.example.managementapi.utils.ManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class  UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> loin(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try{
            return  userService.getAllUser();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserDTO>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}