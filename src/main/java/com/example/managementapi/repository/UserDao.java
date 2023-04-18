package com.example.managementapi.repository;

import com.example.managementapi.dto.UserDTO;
import com.example.managementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserDTO> getAllUser();


    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
}
