package org.example.mercadolibre.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class DnaRequest {
    @NotNull(message = "DNA sequence cannot be null")
    @Size(min = 1, message = "DNA sequence cannot be empty")
    private String[] dna;
}
