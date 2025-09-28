package com.agritech.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritech.entity.Farmer;
import com.agritech.repo.FarmerRepo;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerRepo farmerRepository;

    public FarmerController(FarmerRepo farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    // Get all farmers
    @GetMapping
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    // Get farmer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) {
        Optional<Farmer> farmer = farmerRepository.findById(id);
        return farmer.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Create new farmer
    @PostMapping
    public Farmer createFarmer(@RequestBody Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    // Update farmer
    @PutMapping("/{id}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable Long id, @RequestBody Farmer farmerDetails) {
        return farmerRepository.findById(id)
                .map(farmer -> {
                    farmer.setName(farmerDetails.getName());
                    farmer.setMobile(farmerDetails.getMobile());
                    farmer.setVillage(farmerDetails.getVillage());
                    farmer.setConsentGiven(farmerDetails.isConsentGiven());
                    farmer.setConsentTimestamp(farmerDetails.getConsentTimestamp());
                    return ResponseEntity.ok(farmerRepository.save(farmer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete farmer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        return farmerRepository.findById(id)
                .map(farmer -> {
                    farmerRepository.delete(farmer);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}