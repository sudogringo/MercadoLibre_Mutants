package org.example.mercadolibre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mercadolibre.dto.DnaRequest;
import org.example.mercadolibre.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DnaRecordRepository dnaRecordRepository;

    @BeforeEach
    void setUp() {
        dnaRecordRepository.deleteAll(); // Clear database before each test
    }

    @Test
    void isMutant_shouldReturnOk_whenDnaIsMutant() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}; // Example mutant DNA
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verify stats
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(1))
                .andExpect(jsonPath("$.countHumanDna").value(0))
                .andExpect(jsonPath("$.ratio").value(1.0));
    }

    @Test
    void isMutant_shouldReturnForbidden_whenDnaIsHuman() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGACGG","GCGTCA","TCACTG"}; // Example human DNA
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        // Verify stats
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(0))
                .andExpect(jsonPath("$.countHumanDna").value(1))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }

    @Test
    void isMutant_shouldHandleDuplicateDna() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}; // Example mutant DNA
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        // First request
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Second request with same DNA
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verify stats - should only count once
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(1))
                .andExpect(jsonPath("$.countHumanDna").value(0))
                .andExpect(jsonPath("$.ratio").value(1.0));
    }

    @Test
    void getStats_shouldReturnCorrectRatio() throws Exception {
        // Add one mutant
        String[] mutantDna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DnaRequest mutantRequest = new DnaRequest();
        mutantRequest.setDna(mutantDna);
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mutantRequest)))
                .andExpect(status().isOk());

        // Add two humans
        String[] humanDna1 = {"ATGCGA","CAGTGC","TTATGT","AGACGG","GCGTCA","TCACTG"};
        DnaRequest humanRequest1 = new DnaRequest();
        humanRequest1.setDna(humanDna1);
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(humanRequest1)))
                .andExpect(status().isForbidden());

        String[] humanDna2 = {"GTGCGA","CAGTGC","TTATGT","AGACGG","GCGTCA","TCACTG"};
        DnaRequest humanRequest2 = new DnaRequest();
        humanRequest2.setDna(humanDna2);
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(humanRequest2)))
                .andExpect(status().isForbidden());

        // Verify stats: 1 mutant, 2 humans, ratio 0.5
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(1))
                .andExpect(jsonPath("$.countHumanDna").value(2))
                .andExpect(jsonPath("$.ratio").value(0.5));
    }

    @Test
    void isMutant_shouldReturnBadRequest_forInvalidDnaSize() throws Exception {
        String[] dna = {"ATGC", "CAG", "TTAT", "AGAC"}; // Not NxN
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void isMutant_shouldReturnBadRequest_forInvalidDnaChars() throws Exception {
        String[] dna = {"ATGC", "CAGX", "TTAT", "AGAC"}; // Invalid char 'X'
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void isMutant_shouldReturnBadRequest_forNullDna() throws Exception {
        DnaRequest request = new DnaRequest();
        request.setDna(null); // Null DNA

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void isMutant_shouldReturnBadRequest_forEmptyDna() throws Exception {
        String[] dna = {}; // Empty DNA array
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
