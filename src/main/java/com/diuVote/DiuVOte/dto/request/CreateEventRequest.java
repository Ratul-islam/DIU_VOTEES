package com.diuVote.DiuVOte.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateEventRequest {

    @NotBlank(message = "Event name is required")
    private String name;

    private String description;

    private String eligibilityCriteriaText;

    @NotNull(message = "Candidate eligibility is required")
    private EligibilityCriteriaDto candidateEligibility;

    @NotNull(message = "Voter eligibility is required")
    private EligibilityCriteriaDto voterEligibility;

    @NotNull(message = "Nomination start time is required")
    private Long nominationStartEpochMs;

    @NotNull(message = "Nomination end time is required")
    private Long nominationEndEpochMs;

    @NotNull(message = "Voting start time is required")
    private Long votingStartEpochMs;

    @NotNull(message = "Voting end time is required")
    private Long votingEndEpochMs;
}