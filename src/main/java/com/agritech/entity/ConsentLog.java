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

@Entity
@Table(name = "consent_logs")
public class ConsentLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "farmer_id", nullable = false)
  private Farmer farmer;

  @Column(nullable = false)
  private boolean consentValue;

  @Column(nullable = false)
  private Long recordedAt;

  private String note;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Farmer getFarmer() {
    return farmer;
  }

  public void setFarmer(Farmer farmer) {
    this.farmer = farmer;
  }

  public boolean isConsentValue() {
    return consentValue;
  }

  public void setConsentValue(boolean consentValue) {
    this.consentValue = consentValue;
  }

  public Long getRecordedAt() {
    return recordedAt;
  }

  public void setRecordedAt(Long recordedAt) {
    this.recordedAt = recordedAt;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}