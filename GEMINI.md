# GEMINI Plan: Mercadolibre Mutant Challenge

This document outlines the step-by-step plan to develop the solution for the Mercadolibre Mutant Detection challenge, as described in the project requirements.

## Phase 1: Core Logic - `isMutant` Function

The first step is to build the core logic for identifying a mutant.

1.  **Algorithm Design:** I will design and implement an efficient algorithm within a dedicated class (e.g., `MutantDetector.java`). This algorithm will be responsible for the `isMutant(String[] dna)` method.
2.  **Sequence Detection:** The logic will scan the provided `NxN` DNA matrix to find sequences of four identical, consecutive characters. The search will be performed in three directions:
    *   Horizontally
    *   Vertically
    *   Diagonally (both primary and parallel diagonals)
3.  **Efficiency:** To ensure efficiency, the algorithm will stop scanning and return `true` as soon as more than one mutant sequence is found.
4.  **Unit Testing:** I will create a comprehensive suite of JUnit tests for the `isMutant` method. These tests will cover various scenarios, including mutant cases, non-mutant cases, edge cases with empty or invalid matrices, and large matrices.

## Phase 2: API Development - `/mutant` Endpoint

With the core logic in place, I will expose it through a REST API using the Spring Boot framework.

1.  **Spring Boot Setup:** I will configure a new Spring Boot application. Dependencies for Spring Web will be added to the `build.gradle` file.
2.  **DTO Creation:** A Data Transfer Object (e.g., `DnaRequest.java`) will be created to model the incoming JSON request body: `{"dna": [...]}`.
3.  **Controller Implementation:** A `MutantController` will be created to handle incoming web requests.
4.  **`/mutant` Endpoint:** I will implement the `POST /mutant` endpoint. This endpoint will:
    *   Receive the DNA sequence from the request body.
    *   Call the `isMutant` method from the core logic.
    *   Return an HTTP `200 OK` status if the DNA belongs to a mutant.
    *   Return an HTTP `403 Forbidden` status if the DNA belongs to a human.

## Phase 3: Database and Statistics - `/stats` Endpoint

This phase involves adding persistence and a new endpoint for statistical analysis.

1.  **Database Integration:** I will add Spring Data JPA and the H2 Database Engine dependencies to the `build.gradle` file and configure the H2 in-memory database in the `application.properties` file.
2.  **JPA Entity:** I will create a `DnaRecord` JPA entity to store information about each verified DNA. To optimize storage and lookups, I will store a hash of the DNA sequence along with a boolean flag indicating if it's a mutant. This prevents storing duplicate DNA sequences.
3.  **Repository Layer:** A `DnaRecordRepository` interface will be created, extending Spring Data's `JpaRepository` to handle database operations.
4.  **Service Layer:** The business logic will be refactored into service classes (`MutantService`, `StatsService`):
    *   `MutantService` will orchestrate the process: check if a DNA has been analyzed before, call the detector if not, and save the result to the database.
    *   `StatsService` will be responsible for querying the database and calculating the required statistics.
5.  **`/stats` Endpoint:** I will implement the `GET /stats` endpoint in the `MutantController`. This endpoint will return a JSON object containing:
    *   `count_mutant_dna`: Total count of mutant DNA sequences.
    *   `count_human_dna`: Total count of human DNA sequences.
    *   `ratio`: The ratio of mutants to humans.

## Phase 4: Quality Assurance and Documentation

To ensure the project is robust and easy to use, I will focus on testing and documentation.

1.  **Integration Testing:** I will write integration tests for the `/mutant` and `/stats` endpoints to verify the full request-response cycle.
2.  **Code Coverage:** I will configure and use a code coverage tool like JaCoCo to ensure that the test suite covers more than 80% of the codebase.
3.  **README Update:** I will thoroughly update the `README.md` file with detailed instructions on how to build, run the application locally, and use the API endpoints.

## Phase 5: Deployment Preparation

The final step is to prepare the application for deployment on a cloud platform.

1.  **Containerization:** I will create a `Dockerfile` to package the Spring Boot application into a container image. This ensures a consistent and reproducible deployment environment.
2.  **Deployment Guide:** I will add a section to the `README.md` with instructions on how to deploy the containerized application to a free cloud service like Render.
