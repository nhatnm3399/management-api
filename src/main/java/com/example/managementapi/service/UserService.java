package com.example.managementapi.service;

import com.example.managementapi.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserDTO>> getAllUser();

    ResponseEntity<String> update(Map<String, String> requestMap);
}
