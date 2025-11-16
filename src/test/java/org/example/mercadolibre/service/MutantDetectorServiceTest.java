package org.example.mercadolibre.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorServiceTest {

    private final MutantDetectorService detector = new MutantDetectorService();

    @Test
    void isMutant_shouldReturnTrue_whenMultipleHorizontalSequences() {
        String[] dna = {
            "AAAA",
            "CCCC",
            "TTTT",
            "GGGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldReturnTrue_whenMultipleVerticalSequences() {
        String[] dna = {
            "ACGT",
            "ACGT",
            "ACGT",
            "ACGT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldReturnTrue_whenMultipleDiagonalSequences() {
        String[] dna = {
            "AGCT",
            "CATG",
            "GTAC",
            "TGCA"
        };
        assertTrue(detector.isMutant(dna));
    }
    
    @Test
    void isMutant_shouldReturnTrue_whenMixedSequences() {
        String[] dna = {
            "AAAAT",
            "CCTGA",
            "TTTGC",
            "GGTCA",
            "GGTCA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldReturnTrue_forPdfExample() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isNotMutant_shouldReturnFalse_whenOnlyOneSequence() {
        String[] dna = {
            "AAAA",
            "CTGC",
            "TTAT",
            "AGAG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isNotMutant_shouldReturnFalse_whenNoSequences() {
        String[] dna = {
            "ATGC",
            "CAGT",
            "TTAT",
            "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldThrowException_forInvalidDnaNotSquare() {
        String[] dna = {
            "ATGC",
            "CAG",
            "TTAT",
            "AGAC"
        };
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldThrowException_forInvalidDnaInvalidChars() {
        String[] dna = {
            "ATGC",
            "CAGX",
            "TTAT",
            "AGAC"
        };
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }

    @Test
    void isMutant_shouldThrowException_forNullDna() {
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(null));
    }

    @Test
    void isMutant_shouldThrowException_forEmptyDna() {
        String[] dna = {};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }
}
