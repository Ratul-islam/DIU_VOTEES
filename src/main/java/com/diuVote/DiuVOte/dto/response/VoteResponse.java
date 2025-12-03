package com.diuVote.DiuVOte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteResponse {
    private String voteId;
    private String eventId;
    private String candidateId;
}