package com.agritech.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritech.entity.FundingMilestone;

@Repository
public interface FundingMilestoneRepo extends JpaRepository<FundingMilestone, Long> {
    Optional<FundingMilestone> findByCode(String code);
    List<FundingMilestone> findByAchieved(boolean achieved);
}