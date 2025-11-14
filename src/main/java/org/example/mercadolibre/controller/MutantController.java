package org.example.mercadolibre.controller;

import org.example.mercadolibre.dto.DnaRequest;
import org.example.mercadolibre.service.MutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
public class MutantController {

    private final MutantDetectorService mutantDetectorService;

    @Autowired
    public MutantController(MutantDetectorService mutantDetectorService) {
        this.mutantDetectorService = mutantDetectorService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        if (mutantDetectorService.isMutant(request.getDna())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
