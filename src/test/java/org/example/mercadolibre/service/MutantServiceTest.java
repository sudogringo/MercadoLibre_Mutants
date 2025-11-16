package org.example.mercadolibre.service;

import org.example.mercadolibre.model.DnaRecord;
import org.example.mercadolibre.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetectorService mutantDetectorService;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    private String[] mutantDna;
    private String[] humanDna;
    private String mutantDnaHash;
    private String humanDnaHash;

    @BeforeEach
    void setUp() {
        mutantDna = new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        humanDna = new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGACGG", "GCGTCA", "TCACTG"};
        mutantDnaHash = Arrays.hashCode(mutantDna) + "";
        humanDnaHash = Arrays.hashCode(humanDna) + "";
    }

    @Test
    void analyzeDna_shouldReturnTrueAndSave_whenNewMutantDna() {
        when(dnaRecordRepository.findByDnaHash(mutantDnaHash)).thenReturn(Optional.empty());
        when(mutantDetectorService.isMutant(mutantDna)).thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = mutantService.analyzeDna(mutantDna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(mutantDnaHash);
        verify(mutantDetectorService, times(1)).isMutant(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void analyzeDna_shouldReturnFalseAndSave_whenNewHumanDna() {
        when(dnaRecordRepository.findByDnaHash(humanDnaHash)).thenReturn(Optional.empty());
        when(mutantDetectorService.isMutant(humanDna)).thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = mutantService.analyzeDna(humanDna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(humanDnaHash);
        verify(mutantDetectorService, times(1)).isMutant(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void analyzeDna_shouldReturnTrueFromExistingRecord_whenMutantDnaAlreadyExists() {
        DnaRecord existingMutantRecord = new DnaRecord(mutantDnaHash, true);
        when(dnaRecordRepository.findByDnaHash(mutantDnaHash)).thenReturn(Optional.of(existingMutantRecord));

        boolean result = mutantService.analyzeDna(mutantDna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(mutantDnaHash);
        verify(mutantDetectorService, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    void analyzeDna_shouldReturnFalseFromExistingRecord_whenHumanDnaAlreadyExists() {
        DnaRecord existingHumanRecord = new DnaRecord(humanDnaHash, false);
        when(dnaRecordRepository.findByDnaHash(humanDnaHash)).thenReturn(Optional.of(existingHumanRecord));

        boolean result = mutantService.analyzeDna(humanDna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(humanDnaHash);
        verify(mutantDetectorService, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }
}
