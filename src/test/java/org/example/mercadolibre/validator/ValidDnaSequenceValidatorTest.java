package org.example.mercadolibre.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidDnaSequenceValidatorTest {

    private ValidDnaSequenceValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new ValidDnaSequenceValidator();
    }

    @Test
    void isValid_shouldReturnTrue_forValidDna() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        assertTrue(validator.isValid(dna, context));
    }

    @Test
    void isValid_shouldReturnFalse_forNullDna() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void isValid_shouldReturnFalse_forEmptyDna() {
        String[] dna = {};
        assertFalse(validator.isValid(dna, context));
    }

    @Test
    void isValid_shouldReturnFalse_forNonSquareDna() {
        String[] dna = {"ATGC", "CAGT", "TTA", "AGAC"};
        assertFalse(validator.isValid(dna, context));
    }

    @Test
    void isValid_shouldReturnFalse_forInvalidCharacters() {
        String[] dna = {"ATGC", "CAGX", "TTAT", "AGAC"};
        assertFalse(validator.isValid(dna, context));
    }

    @Test
    void isValid_shouldReturnFalse_forNullRow() {
        String[] dna = {"ATGC", "CAGT", null, "AGAC"};
        assertFalse(validator.isValid(dna, context));
    }
}
