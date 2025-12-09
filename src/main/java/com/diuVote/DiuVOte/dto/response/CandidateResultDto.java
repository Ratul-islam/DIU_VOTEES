package com.diuVote.DiuVOte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResultDto {
    private String candidateId;
    private long votes;
    private double percentage;
}