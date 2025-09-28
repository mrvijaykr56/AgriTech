package com.agritech.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agritech.entity.ConsentLog;
import com.agritech.entity.Farmer;

public interface ConsentLogRepo extends JpaRepository<ConsentLog, Long> {
  List<ConsentLog> findByFarmerOrderByRecordedAtDesc(Farmer farmer);
}