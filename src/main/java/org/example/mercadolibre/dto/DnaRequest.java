package org.example.mercadolibre.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.mercadolibre.validator.ValidDnaSequence;

@Data
@Schema(description = "Request para verificar si un ADN es mutante")
public class DnaRequest {
    @NotNull(message = "DNA sequence cannot be null")
    @Size(min = 1, message = "DNA sequence cannot be empty")
    @ValidDnaSequence
    @ArraySchema(schema = @Schema(description = "Secuencia de ADN representada como matriz NxN", example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]", required = true))
    private String[] dna;
}
