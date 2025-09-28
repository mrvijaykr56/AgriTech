package com.agritech.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritech.entity.FundingMilestone;
import com.agritech.repo.FundingMilestoneRepo;

@RestController
@RequestMapping("/api/milestones")
public class FundingMilestoneController {

    private final FundingMilestoneRepo milestoneRepo;

    public FundingMilestoneController(FundingMilestoneRepo milestoneRepo) {
        this.milestoneRepo = milestoneRepo;
    }

    // ✅ Get all milestones
    @GetMapping
    public List<FundingMilestone> getAll() {
        return milestoneRepo.findAll();
    }

    // ✅ Get milestone by ID
    @GetMapping("/{id}")
    public ResponseEntity<FundingMilestone> getById(@PathVariable Long id) {
        return milestoneRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get milestone by code
    @GetMapping("/code/{code}")
    public ResponseEntity<FundingMilestone> getByCode(@PathVariable String code) {
        return milestoneRepo.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create milestone
    @PostMapping
    public FundingMilestone create(@RequestBody FundingMilestone milestone) {
        return milestoneRepo.save(milestone);
    }

    // ✅ Update milestone
    @PutMapping("/{id}")
    public ResponseEntity<FundingMilestone> update(
            @PathVariable Long id,
            @RequestBody FundingMilestone details) {
        return milestoneRepo.findById(id)
                .map(m -> {
                    m.setTitle(details.getTitle());
                    m.setDescription(details.getDescription());
                    m.setDueDate(details.getDueDate());
                    m.setAchieved(details.isAchieved());
                    m.setAchievedAt(details.getAchievedAt());
                    m.setEvidenceUrl(details.getEvidenceUrl());
                    return ResponseEntity.ok(milestoneRepo.save(m));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete milestone
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return milestoneRepo.findById(id)
                .map(m -> {
                    milestoneRepo.delete(m);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}