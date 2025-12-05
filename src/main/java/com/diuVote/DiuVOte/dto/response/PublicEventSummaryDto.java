package com.diuVote.DiuVOte.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class PublicEventSummaryDto {
    private String id;
    private String name;
    private String description;
    private Instant nominationStart;
    private Instant nominationEnd;
    private Instant votingStart;
    private Instant votingEnd;
}