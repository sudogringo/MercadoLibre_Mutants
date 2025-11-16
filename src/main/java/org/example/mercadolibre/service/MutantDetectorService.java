package org.example.mercadolibre.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetectorService {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MUTANT_SEQUENCES_THRESHOLD = 2;

    public boolean isMutant(String[] dna) {
        final int n = dna.length;
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Check horizontal ->
                if (j <= n - SEQUENCE_LENGTH) {
                    if (hasSequenceAt(matrix, i, j, 0, 1)) {
                        sequencesFound++;
                    }
                }
                // Check vertical V
                if (i <= n - SEQUENCE_LENGTH) {
                    if (hasSequenceAt(matrix, i, j, 1, 0)) {
                        sequencesFound++;
                    }
                }
                // Check diagonal \
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (hasSequenceAt(matrix, i, j, 1, 1)) {
                        sequencesFound++;
                    }
                }
                // Check anti-diagonal /
                if (i >= SEQUENCE_LENGTH - 1 && j <= n - SEQUENCE_LENGTH) {
                    if (hasSequenceAt(matrix, i, j, -1, 1)) {
                        sequencesFound++;
                    }
                }

                if (sequencesFound >= MUTANT_SEQUENCES_THRESHOLD) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasSequenceAt(char[][] matrix, int row, int col, int rowIncrement, int colIncrement) {
        char firstChar = matrix[row][col];
        return matrix[row + rowIncrement][col + colIncrement] == firstChar &&
               matrix[row + 2 * rowIncrement][col + 2 * colIncrement] == firstChar &&
               matrix[row + 3 * rowIncrement][col + 3 * colIncrement] == firstChar;
    }
}
