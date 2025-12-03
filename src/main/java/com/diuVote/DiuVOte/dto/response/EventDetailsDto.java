package com.diuVote.DiuVOte.dto.response;

import com.diuVote.DiuVOte.entity.EligibilityCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class EventDetailsDto {
    private String id;
    private String name;
    private String description;

    private EligibilityCriteria candidateEligibility;
    private EligibilityCriteria voterEligibility;

    private Instant nominationStart;
    private Instant nominationEnd;
    private Instant votingStart;
    private Instant votingEnd;
    private boolean nominationsClosedManually;
    private boolean votingClosedManually;
    private boolean resultsReleased;
    private List<CandidateDto> candidates;
}