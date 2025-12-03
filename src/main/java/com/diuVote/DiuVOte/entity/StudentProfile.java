package com.diuVote.DiuVOte.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "student_profiles")
public class StudentProfile {

    @Id
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