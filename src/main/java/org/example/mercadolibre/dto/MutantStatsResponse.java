package org.example.mercadolibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estadísticas de verificaciones de ADN")
public class MutantStatsResponse {
    @Schema(description = "Cantidad de ADN mutante verificado")
    private long countMutantDna;
    @Schema(description = "Cantidad de ADN humano verificado")
    private long countHumanDna;
    @Schema(description = "Ratio: mutantes / humanos")
    private double ratio;
}
