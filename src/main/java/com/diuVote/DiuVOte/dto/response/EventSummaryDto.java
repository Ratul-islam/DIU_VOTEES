package com.diuVote.DiuVOte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventSummaryDto {
    private String id;
    private String name;
    private String description;
}