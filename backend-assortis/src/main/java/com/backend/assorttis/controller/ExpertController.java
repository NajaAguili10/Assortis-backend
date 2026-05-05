package com.backend.assorttis.controller;

import com.backend.assorttis.dto.expert.ExpertDTO;
import com.backend.assorttis.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping
    public ResponseEntity<List<ExpertDTO>> getAllExperts() {
        return ResponseEntity.ok(expertService.getAllExperts());
    }
}
