package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.request.CreateEventRequest;
import com.diuVote.DiuVOte.dto.request.ModerateCandidateRequest;
import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.EventDetailsDto;
import com.diuVote.DiuVOte.dto.response.EventSummaryDto;
import com.diuVote.DiuVOte.service.EventCreatorEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-creators")
@RequiredArgsConstructor
public class EventCreatorEventController {

    private final EventCreatorEventService eventService;

    @PostMapping("/events")
    public ResponseEntity<ApiResponse<EventDetailsDto>> createEvent(
            Authentication authentication,
            @Valid @RequestBody CreateEventRequest request
    ) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        EventDetailsDto event = eventService.createEvent(creatorId, creatorEmail, request);

        ApiResponse<EventDetailsDto> response = new ApiResponse<>(
                true,
                "Event created successfully",
                event
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events")
    public ResponseEntity<ApiResponse<List<EventSummaryDto>>> listMyEvents(Authentication authentication) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        List<EventSummaryDto> events = eventService.listMyEvents(creatorId);

        ApiResponse<List<EventSummaryDto>> response = new ApiResponse<>(
                true,
                "Events fetched successfully",
                events
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<EventDetailsDto>> getMyEventDetails(
            Authentication authentication,
            @PathVariable String eventId
    ) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        EventDetailsDto event = eventService.getMyEventDetails(creatorId, eventId);

        ApiResponse<EventDetailsDto> response = new ApiResponse<>(
                true,
                "Event details fetched successfully",
                event
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/events/{eventId}/candidates/{candidateId}/moderate")
    public ResponseEntity<ApiResponse<EventDetailsDto>> moderateCandidate(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String candidateId,
            @Valid @RequestBody ModerateCandidateRequest request
    ) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        EventDetailsDto updated = eventService.moderateCandidate(creatorId, eventId, candidateId, request);

        String message = Boolean.TRUE.equals(request.getApprove())
                ? "Candidate approved"
                : "Candidate rejected";

        ApiResponse<EventDetailsDto> response = new ApiResponse<>(
                true,
                message,
                updated
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/events/{eventId}/end-voting")
    public ResponseEntity<ApiResponse<EventDetailsDto>> endVoting(
            Authentication authentication,
            @PathVariable String eventId
    ) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        EventDetailsDto updated = eventService.endVoting(creatorId, eventId);

        ApiResponse<EventDetailsDto> response = new ApiResponse<>(
                true,
                "Voting ended for this event",
                updated
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/events/{eventId}/release-results")
    public ResponseEntity<ApiResponse<EventDetailsDto>> releaseResults(
            Authentication authentication,
            @PathVariable String eventId
    ) {
        String creatorEmail = authentication.getName();
        String creatorId = creatorEmail;

        EventDetailsDto updated = eventService.releaseResults(creatorId, eventId);

        ApiResponse<EventDetailsDto> response = new ApiResponse<>(
                true,
                "Results released for this event",
                updated
        );

        return ResponseEntity.ok(response);
    }
}