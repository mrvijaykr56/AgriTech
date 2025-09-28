package com.agritech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long fieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String crop;

    @Min(1)
    @Column(name = "area_sqm")
    private Integer areaSqM;

    // Getters and Setters
    public Long getFieldId() { return fieldId; }
    public void setFieldId(Long fieldId) { this.fieldId = fieldId; }

    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    public String getCrop() { return crop; }
    public void setCrop(String crop) { this.crop = crop; }

    public Integer getAreaSqM() { return areaSqM; }
    public void setAreaSqM(Integer areaSqM) { this.areaSqM = areaSqM; }

    @Override
    public String toString() {
        return "Field{" +
                "fieldId=" + fieldId +
                ", crop='" + crop + '\'' +
                ", areaSqM=" + areaSqM +
                '}';
    }
}