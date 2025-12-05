package com.diuVote.DiuVOte.dto.response;

import com.diuVote.DiuVOte.entity.EligibilityCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicEventDetailsDto {

    private String id;

    private String name;
    private String description;

    private String creatorId;
    private String creatorEmail;

    private Instant nominationStart;
    private Instant nominationEnd;
    private Instant votingStart;
    private Instant votingEnd;

    private boolean nominationsClosedManually;
    private boolean votingClosedManually;
    private boolean resultsReleased;

    private EligibilityCriteria candidateEligibility;
    private EligibilityCriteria voterEligibility;

    private List<String> candidateIds;
}