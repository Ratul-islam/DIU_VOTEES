package com.diuVote.DiuVOte.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "votes")
public class VOTE {

    @Id
    private String id;

    private String eventId;
    private String candidateId;

    private String voterNfcId;

    private String voterDepartment;
    private String voterSession;
    private String voterSection;
    private Integer voterBatch;

    private Instant createdAt = Instant.now();
}