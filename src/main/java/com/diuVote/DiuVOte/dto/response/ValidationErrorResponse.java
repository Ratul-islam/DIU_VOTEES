package com.diuVote.DiuVOte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private Map<String, List<String>> errors;
}