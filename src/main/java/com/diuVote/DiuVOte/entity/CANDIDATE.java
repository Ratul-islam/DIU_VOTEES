package com.diuVote.DiuVOte.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "candidates")
public class CANDIDATE {

    @Id
    private String id;

    private String eventId;

    private String name;
    private String bio;
    private String manifesto;

    private String nomineeNfcId;

    private CandidateStatus status = CandidateStatus.PENDING;

    private Instant createdAt = Instant.now();
    private Instant approvedAt;
    private Instant rejectedAt;

    public enum CandidateStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}