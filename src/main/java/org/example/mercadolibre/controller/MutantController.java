package org.example.mercadolibre.controller;

import org.example.mercadolibre.dto.AnalysisResult;
import org.example.mercadolibre.dto.DnaRequest;
import org.example.mercadolibre.dto.MutantStatsResponse;
import org.example.mercadolibre.service.MutantService;
import org.example.mercadolibre.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detección de mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Verificar si un ADN es mutante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Es mutante",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalysisResult.class))),
        @ApiResponse(responseCode = "403", description = "No es mutante",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalysisResult.class))),
        @ApiResponse(responseCode = "400", description = "ADN inválido", content = @Content)
    })
    public ResponseEntity<AnalysisResult> isMutant(@Validated @RequestBody DnaRequest request) {
        if (mutantService.analyzeDna(request.getDna())) {
            return ResponseEntity.ok(new AnalysisResult("mutant"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AnalysisResult("human"));
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de ADN mutante y humano")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    })
    public ResponseEntity<MutantStatsResponse> getStats() {
        MutantStatsResponse stats = statsService.getMutantStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
