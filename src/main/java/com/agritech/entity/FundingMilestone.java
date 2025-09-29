package com.agritech.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "funding_milestones")
public class FundingMilestone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String code;

  @Column(length = 255)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private boolean achieved = false;

  private Long achievedAt;

  @Column(length = 512)
  private String evidenceUrl;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate  dueDate) {
    this.dueDate = dueDate;
  }

  public boolean isAchieved() {
    return achieved;
  }

  public void setAchieved(boolean achieved) {
    this.achieved = achieved;
  }

  public Long getAchievedAt() {
    return achievedAt;
  }

  public void setAchievedAt(Long achievedAt) {
    this.achievedAt = achievedAt;
  }

  public String getEvidenceUrl() {
    return evidenceUrl;
  }

  public void setEvidenceUrl(String evidenceUrl) {
    this.evidenceUrl = evidenceUrl;
  }
}