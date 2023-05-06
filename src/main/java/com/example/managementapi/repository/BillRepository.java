package com.example.managementapi.repository;

import com.example.managementapi.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository <Bill, Integer>{

    List<Bill> getAllBills();

    List<Bill> getBillByUserName(@Param("username") String username);
}
