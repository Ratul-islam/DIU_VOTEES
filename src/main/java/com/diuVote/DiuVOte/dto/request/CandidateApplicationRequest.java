package com.diuVote.DiuVOte.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CandidateApplicationRequest {

    @NotBlank(message = "NFC ID is required")
    private String nfcId;

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Candidate name is required")
    private String name;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Manifesto is required")
    private String manifesto;
}