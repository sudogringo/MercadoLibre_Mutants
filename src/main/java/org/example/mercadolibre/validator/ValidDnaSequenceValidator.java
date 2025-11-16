package org.example.mercadolibre.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false; // Or handle as per requirements, guide implies this is invalid
        }

        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false; // Not an NxN matrix
            }
            for (char base : row.toCharArray()) {
                if (!VALID_BASES.contains(base)) {
                    return false; // Contains invalid characters
                }
            }
        }
        return true;
    }
}
