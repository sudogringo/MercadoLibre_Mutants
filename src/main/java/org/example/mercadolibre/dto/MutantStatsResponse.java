package org.example.mercadolibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutantStatsResponse {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
