package com.diuVote.DiuVOte.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VoteRequest {

    @NotBlank(message = "NFC ID is required")
    private String nfcId;

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Candidate ID is required")
    private String candidateId;
}