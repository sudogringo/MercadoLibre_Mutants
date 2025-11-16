package org.example.mercadolibre.service;

import org.example.mercadolibre.model.DnaRecord;
import org.example.mercadolibre.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MutantService {

    private final MutantDetectorService mutantDetectorService;
    private final DnaRecordRepository dnaRecordRepository;

    @Autowired
    public MutantService(MutantDetectorService mutantDetectorService, DnaRecordRepository dnaRecordRepository) {
        this.mutantDetectorService = mutantDetectorService;
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public boolean analyzeDna(String[] dna) {
        String dnaHash = calculateDnaHash(dna);
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);

        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        boolean isMutant = mutantDetectorService.isMutant(dna);
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    private String calculateDnaHash(String[] dna) {
        return Arrays.hashCode(dna) + ""; // Simple hash for now, can be improved
    }
}
