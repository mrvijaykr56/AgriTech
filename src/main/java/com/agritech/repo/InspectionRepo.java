package com.agritech.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritech.entity.Field;
import com.agritech.entity.Inspection;

@Repository
public interface InspectionRepo extends JpaRepository<Inspection, String> {

    // Find inspections by field
    List<Inspection> findByField(Field field);

    // Find inspections by commodity
    List<Inspection> findByCommodity(String commodity);

    // Find inspections by risk level
    List<Inspection> findByRiskLevel(String riskLevel);

    // Find inspections for a field ordered by creation time
    List<Inspection> findByFieldOrderByCreatedAtDesc(Field field);
}