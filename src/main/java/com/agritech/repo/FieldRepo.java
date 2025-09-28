package com.agritech.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agritech.entity.Field;

public interface FieldRepo extends JpaRepository<Field, Long> {

    // Find all fields belonging to a farmer by farmerId
    List<Field> findByFarmer_FarmerId(Long farmerId);

    // Optionally, find by crop
    List<Field> findByCrop(String crop);

    // Optionally, find by farmer name
    List<Field> findByFarmer_Name(String name);
}