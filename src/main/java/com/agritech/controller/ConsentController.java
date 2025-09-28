package com.agritech.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agritech.entity.ConsentLog;
import com.agritech.entity.Farmer;
import com.agritech.repo.ConsentLogRepo;
import com.agritech.repo.FarmerRepo;

@RestController
@RequestMapping("/api/consent")
public class ConsentController {

    private final FarmerRepo farmerRepo;
    private final ConsentLogRepo logRepo;

    public ConsentController(FarmerRepo farmerRepo, ConsentLogRepo logRepo) {
        this.farmerRepo = farmerRepo;
        this.logRepo = logRepo;
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<ConsentDetails> getConsentDetails(@PathVariable Long farmerId) {
        return farmerRepo.findById(farmerId)
                .map(farmer -> {
                    var logs = logRepo.findByFarmerOrderByRecordedAtDesc(farmer);
                    return ResponseEntity.ok(new ConsentDetails(farmer, logs));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{farmerId}/record")
    public ResponseEntity<String> recordConsent(
            @PathVariable Long farmerId,
            @RequestParam boolean value,
            @RequestParam(required = false) String note
    ) {
        return farmerRepo.findById(farmerId)
                .map(farmer -> {
                    farmer.setConsentGiven(value);
                    farmer.setConsentTimestamp(Instant.now());
                    farmerRepo.save(farmer);

                    var log = new ConsentLog();
                    log.setFarmer(farmer);
                    log.setConsentValue(value);
                    log.setRecordedAt(System.currentTimeMillis());
                    log.setNote(note);
                    logRepo.save(log);

                    return ResponseEntity.ok("Consent recorded");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    static class ConsentDetails {
        public Farmer farmer;
        public List<ConsentLog> logs;

        public ConsentDetails(Farmer farmer, List<ConsentLog> logs) {
            this.farmer = farmer;
            this.logs = logs;
        }
    }
}