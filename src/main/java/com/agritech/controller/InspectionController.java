package com.agritech.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agritech.dto.InspectionDTO;
import com.agritech.entity.Field;
import com.agritech.entity.Inspection;
import com.agritech.repo.FieldRepo;
import com.agritech.repo.InspectionRepo;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    private final InspectionRepo inspectionRepo;
    private final FieldRepo fieldRepo;

    public InspectionController(InspectionRepo inspectionRepo, FieldRepo fieldRepo) {
        this.inspectionRepo = inspectionRepo;
        this.fieldRepo = fieldRepo;
    }

    // ✅ Get all inspections (as DTOs)
    @GetMapping
    public List<InspectionDTO> getAllInspections() {
        return inspectionRepo.findAll()
                .stream()
                .map(InspectionDTO::fromEntity)
                .toList();
    }

    // ✅ Get inspection by ID
    @GetMapping("/{id}")
    public ResponseEntity<InspectionDTO> getInspectionById(@PathVariable String id) {
        return inspectionRepo.findById(id)
                .map(InspectionDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get inspections by fieldId
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<InspectionDTO>> getInspectionsByField(@PathVariable Long fieldId) {
        return fieldRepo.findById(fieldId)
                .map(field -> inspectionRepo.findByFieldOrderByCreatedAtDesc(field)
                        .stream()
                        .map(InspectionDTO::fromEntity)
                        .toList())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create new inspection
    @PostMapping
    public ResponseEntity<InspectionDTO> createInspection(@RequestBody Inspection inspection) {
        if (inspection.getField() == null || inspection.getField().getFieldId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Field field = fieldRepo.findById(inspection.getField().getFieldId()).orElse(null);
        if (field == null) return ResponseEntity.notFound().build();

        inspection.setField(field);
        Inspection saved = inspectionRepo.save(inspection);
        return ResponseEntity.ok(InspectionDTO.fromEntity(saved));
    }

    // ✅ Delete inspection
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable String id) {
        return inspectionRepo.findById(id)
                .map(inspection -> {
                    inspectionRepo.delete(inspection);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}