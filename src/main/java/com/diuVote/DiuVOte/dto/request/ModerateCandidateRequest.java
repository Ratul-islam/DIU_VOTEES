package com.diuVote.DiuVOte.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModerateCandidateRequest {

    @NotNull(message = "approve flag is required")
    private Boolean approve;
}