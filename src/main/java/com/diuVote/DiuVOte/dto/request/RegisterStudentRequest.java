package com.diuVote.DiuVOte.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterStudentRequest {

    @NotBlank(message = "NFC ID is required")
    private String nfcId;

    @NotBlank(message = "Student ID is required")
    private String studentId;
    @NotBlank(message = "Student Name is required")
    private String name;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Faculty is required")
    private String faculty;

    @NotBlank(message = "Session is required")
    private String session;

    @NotBlank(message = "Section is required")
    private String section;
    
    @NotNull(message = "Batch is required")
    private Integer batch;
}