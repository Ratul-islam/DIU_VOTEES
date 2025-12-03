package com.diuVote.DiuVOte.dto.response;

import com.diuVote.DiuVOte.entity.CANDIDATE;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateApplicationResponse {
    private String candidateId;
    private String eventId;
    private String name;
    private String bio;
    private String manifesto;
    private CANDIDATE.CandidateStatus status;
}