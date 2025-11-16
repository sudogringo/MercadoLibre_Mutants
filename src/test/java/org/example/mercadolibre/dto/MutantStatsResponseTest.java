package org.example.mercadolibre.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantStatsResponseTest {

    @Test
    void testMutantStatsResponse() {
        MutantStatsResponse response = new MutantStatsResponse();
        response.setCountMutantDna(40);
        response.setCountHumanDna(100);
        response.setRatio(0.4);

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testMutantStatsResponseAllArgsConstructor() {
        MutantStatsResponse response = new MutantStatsResponse(40, 100, 0.4);

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }
}
