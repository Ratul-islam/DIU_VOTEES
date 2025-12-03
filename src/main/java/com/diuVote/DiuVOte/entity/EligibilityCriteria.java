package com.diuVote.DiuVOte.entity;

import lombok.Data;

import java.util.List;

@Data
public class EligibilityCriteria {

    private List<String> allowedDepartments;

    private List<String> allowedSessions;

    private List<String> allowedSections;

    private List<Integer> allowedBatches;

    private Integer batchRangeStart;
    private Integer batchRangeEnd;
}