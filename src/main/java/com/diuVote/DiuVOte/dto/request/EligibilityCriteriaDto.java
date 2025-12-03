package com.diuVote.DiuVOte.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class EligibilityCriteriaDto {

    private List<String> allowedDepartments;
    private List<String> allowedSessions;
    private List<String> allowedSections;
    private List<Integer> allowedBatches;
    private Integer batchRangeStart;
    private Integer batchRangeEnd;
}