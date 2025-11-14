package org.example.mercadolibre.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class MutantDetectorService {

    private static final Pattern DNA_PATTERN = Pattern.compile("[ATCG]+");

    public boolean isMutant(String[] dna) {
        if (!isValid(dna)) {
            // Or throw an exception, depending on desired behavior for invalid input.
            // For this challenge, returning false or throwing is acceptable.
            // Let's stick to the spec of boolean return.
            return false;
        }

        int sequencesFound = 0;
        int n = dna.length;

        // Check horizontal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (dna[i].charAt(j) == dna[i].charAt(j + 1) &&
                    dna[i].charAt(j) == dna[i].charAt(j + 2) &&
                    dna[i].charAt(j) == dna[i].charAt(j + 3)) {
                    sequencesFound++;
                    if (sequencesFound > 1) return true;
                }
            }
        }

        // Check vertical
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= n - 4; i++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j)) {
                    sequencesFound++;
                    if (sequencesFound > 1) return true;
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j + 1) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j + 2) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j + 3)) {
                    sequencesFound++;
                    if (sequencesFound > 1) return true;
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 3; j < n; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j - 1) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j - 2) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j - 3)) {
                    sequencesFound++;
                    if (sequencesFound > 1) return true;
                }
            }
        }

        return sequencesFound > 1;
    }

    private boolean isValid(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n || !DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        return true;
    }
}
