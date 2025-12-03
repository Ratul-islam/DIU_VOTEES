package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.CreateEventRequest;
import com.diuVote.DiuVOte.dto.request.ModerateCandidateRequest;
import com.diuVote.DiuVOte.dto.response.EventDetailsDto;
import com.diuVote.DiuVOte.dto.response.EventSummaryDto;

import java.util.List;

public interface EventCreatorEventService {

    EventDetailsDto createEvent(String creatorId, String creatorEmail, CreateEventRequest request);

    List<EventSummaryDto> listMyEvents(String creatorId);

    EventDetailsDto getMyEventDetails(String creatorId, String eventId);

    EventDetailsDto moderateCandidate(String creatorId, String eventId, String candidateId, ModerateCandidateRequest request);

    EventDetailsDto endVoting(String creatorId, String eventId);

    EventDetailsDto releaseResults(String creatorId, String eventId);
}