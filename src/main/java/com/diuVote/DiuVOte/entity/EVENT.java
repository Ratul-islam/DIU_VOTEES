package com.diuVote.DiuVOte.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "events")
public class EVENT {

    @Id
    private String id;

    private String name;
    private String description;

    private String creatorId;      
    private String creatorEmail;   

    private Instant nominationStart;
    private Instant nominationEnd;
    private Instant votingStart;
    private Instant votingEnd;

    private boolean nominationsClosedManually = false;
    private boolean votingClosedManually = false;
    private boolean resultsReleased = false;

    private EligibilityCriteria candidateEligibility;
    private EligibilityCriteria voterEligibility;

    private List<String> candidateIds;
}