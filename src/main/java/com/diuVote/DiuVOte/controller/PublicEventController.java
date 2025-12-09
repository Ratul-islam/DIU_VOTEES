package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.EventDetailsDto;
import com.diuVote.DiuVOte.dto.response.PublicEventSummaryDto;
import com.diuVote.DiuVOte.service.EventCreatorEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventCreatorEventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PublicEventSummaryDto>>> getAllEvents() {
        List<PublicEventSummaryDto> events = eventService.listAllPublicEvents();

        ApiResponse<List<PublicEventSummaryDto>> response =
                new ApiResponse<>(true, "Events fetched successfully", events);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventDetailsDto>> getEventById(
            @PathVariable("id") String id) {
                
        EventDetailsDto event = eventService.getPublicEventDetails(id);

        ApiResponse<EventDetailsDto> response =
                new ApiResponse<>(true, "Event fetched successfully", event);

        return ResponseEntity.ok(response);
    }
}