package com.diuVote.DiuVOte.dto.response;

import com.diuVote.DiuVOte.entity.EligibilityCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
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

    // --- New fields for on-demand results/statistics ---
    /**
     * true when service computed/attached results (voting ended or resultsReleased flag)
     */
    private boolean resultsAvailable;

    /**
     * total number of votes cast in this event (sum of all candidate counts)
     */
    private long totalVotes;

    /**
     * per-candidate results (candidateId, optional candidateName, count and percent)
     */
    private List<CandidateResultDto> results;
}