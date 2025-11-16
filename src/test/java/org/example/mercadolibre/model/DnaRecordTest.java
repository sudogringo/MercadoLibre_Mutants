package org.example.mercadolibre.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaRecordTest {

    @Test
    void testDnaRecord() {
        DnaRecord record = new DnaRecord();
        record.setId(1L);
        record.setDnaHash("some_hash");
        record.setMutant(true);

        assertEquals(1L, record.getId());
        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
    }

    @Test
    void testDnaRecordAllArgsConstructor() {
        DnaRecord record = new DnaRecord(1L, "some_hash", true);

        assertEquals(1L, record.getId());
        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
    }

    @Test
    void testDnaRecordConstructorWithoutId() {
        DnaRecord record = new DnaRecord("some_hash", true);

        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
    }
}
