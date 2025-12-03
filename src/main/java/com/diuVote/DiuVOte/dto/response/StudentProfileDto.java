package com.diuVote.DiuVOte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentProfileDto {
    private String id;
    private String nfcId;
    private String studentId;
    private String name;
    private String department;
    private String faculty;
    private String session;
    private String section;
    private Integer batch;
}