package org.example.mercadolibre.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DnaRecordTest {

    @Test
    void testDnaRecord() {
        DnaRecord record = new DnaRecord();
        record.setId(1L);
        record.setDnaHash("some_hash");
        record.setMutant(true);
        record.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, record.getId());
        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
        assertNotNull(record.getCreatedAt());
    }

    @Test
    void testDnaRecordAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        DnaRecord record = new DnaRecord(1L, "some_hash", true, now);

        assertEquals(1L, record.getId());
        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
        assertEquals(now, record.getCreatedAt());
    }

    @Test
    void testDnaRecordConstructorWithoutId() {
        DnaRecord record = new DnaRecord("some_hash", true);

        assertEquals("some_hash", record.getDnaHash());
        assertTrue(record.isMutant());
        assertNotNull(record.getCreatedAt()); // Assert that createdAt is set by the constructor
    }
}
