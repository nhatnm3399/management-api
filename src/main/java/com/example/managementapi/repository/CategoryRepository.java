package com.example.managementapi.repository;

import com.example.managementapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();

}
