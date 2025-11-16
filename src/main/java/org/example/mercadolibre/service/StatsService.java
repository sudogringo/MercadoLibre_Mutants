package org.example.mercadolibre.service;

import org.example.mercadolibre.dto.MutantStatsResponse;
import org.example.mercadolibre.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    @Autowired
    public StatsService(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public MutantStatsResponse getMutantStats() {
        long countMutantDna = dnaRecordRepository.countByIsMutant(true);
        long countHumanDna = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (countHumanDna > 0) {
            ratio = (double) countMutantDna / countHumanDna;
        } else if (countMutantDna > 0) {
            ratio = 1.0; // Or handle as per specific requirement, e.g., infinity or a very high number
        }

        return new MutantStatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
