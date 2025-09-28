package com.agritech.repo;

import com.agritech.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepo extends JpaRepository<Farmer, Long> {

    // Already available from JpaRepository:
    // Optional<Farmer> findById(Long id);

    // Custom query methods
    Optional<Farmer> findByMobile(String mobile);

    List<Farmer> findByName(String name);

    List<Farmer> findByVillage(String village);

    // Example: find all farmers who have given consent
    List<Farmer> findByConsentGivenTrue();

    // Example: find by name containing (case-insensitive search)
    List<Farmer> findByNameContainingIgnoreCase(String keyword);
}