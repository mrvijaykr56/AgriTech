package com.agritech.dto;

import java.time.Instant;

import com.agritech.entity.Inspection;

public record InspectionDTO(
		Long inspectionId,
        Long fieldId,
        String commodity,
        String label,
        double confidence,
        String riskLevel,
        Double latitude,
        Double longitude,
        Instant createdAt
) {
    // Factory method to map from entity
    public static InspectionDTO fromEntity(Inspection inspection) {
        return new InspectionDTO(
                inspection.getInspectionId(),
                inspection.getField() != null ? inspection.getField().getFieldId() : null,
                inspection.getCommodity(),
                inspection.getLabel(),
                inspection.getConfidence(),
                inspection.getRiskLevel(),
                inspection.getLatitude(),
                inspection.getLongitude(),
                inspection.getCreatedAt()
        );
    }
}