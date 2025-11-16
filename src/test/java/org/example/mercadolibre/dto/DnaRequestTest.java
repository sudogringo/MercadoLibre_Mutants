package org.example.mercadolibre.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaRequestTest {

    @Test
    void testDnaRequest() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        request.setDna(dna);
        assertArrayEquals(dna, request.getDna());
    }
}
