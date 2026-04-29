package com.backend.assorttis.controller;

import com.backend.assorttis.dto.joboffer.JobOfferRequestDTO;
import com.backend.assorttis.dto.joboffer.JobOfferResponseDTO;
import com.backend.assorttis.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @GetMapping
    public ResponseEntity<List<JobOfferResponseDTO>> getAllJobOffers() {
        return ResponseEntity.ok(jobOfferService.getAllJobOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponseDTO> getJobOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(jobOfferService.getJobOfferById(id));
    }

    @PostMapping
    public ResponseEntity<JobOfferResponseDTO> createJobOffer(@RequestBody JobOfferRequestDTO request) {
        return ResponseEntity.ok(jobOfferService.createJobOffer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOfferResponseDTO> updateJobOffer(
            @PathVariable Long id,
            @RequestBody JobOfferRequestDTO request
    ) {
        return ResponseEntity.ok(jobOfferService.updateJobOffer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }
}
