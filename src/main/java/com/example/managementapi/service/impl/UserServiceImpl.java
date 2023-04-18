package com.example.managementapi.service.impl;

import com.example.managementapi.config.CustomerUserDetailsService;
import com.example.managementapi.config.JwtFilter;
import com.example.managementapi.config.JwtUtil;
import com.example.managementapi.dto.UserDTO;
import com.example.managementapi.model.User;
import com.example.managementapi.constants.ManagementConstants;
import com.example.managementapi.repository.UserDao;
import com.example.managementapi.service.UserService;
import com.example.managementapi.utils.ManagementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;



//            SignUp
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup{}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {

                    userDao.save(getUserFromMap(requestMap));
                return ManagementUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return ManagementUtils.getResponseEntity("Email already exits.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ManagementUtils.getResponseEntity(ManagementConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//            Login
@Override
public ResponseEntity<String> login(Map<String, String> requestMap) {
    log.info("Inside login");
    try {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
        if (auth.isAuthenticated()) {
            if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                return new ResponseEntity<String>("{\"token\":\"" +
                        jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                customerUserDetailsService.getUserDetail().getRole()) + "\"}",
                        HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("{\"message\":\""+"wait for admin approval. "+"\")", HttpStatus.BAD_REQUEST);
            }
        }
    } catch (Exception ex) {
            log.error("{}",ex);
    }

    return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials. "+"\")",
            HttpStatus.BAD_REQUEST);

}

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try{
            if (jwtFilter.isAdmin()){
            return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isUser()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return ManagementUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                }else{
                    ManagementUtils.getResponseEntity("User is does not exits", HttpStatus.OK);
                }
            }else{
                return ManagementUtils.getResponseEntity(ManagementConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignUpMap(Map<String, String> requestMap){
      if ( requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
          return true;
      }
      return false;
    }
    private User getUserFromMap(Map<String, String> requestMap){
        User user = new  User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole(requestMap.get("user"));
        return user;
    }

}
