package com.diuVote.DiuVOte.service.impl;

import com.diuVote.DiuVOte.dto.request.CreateEventRequest;
import com.diuVote.DiuVOte.dto.request.EligibilityCriteriaDto;
import com.diuVote.DiuVOte.dto.request.ModerateCandidateRequest;
import com.diuVote.DiuVOte.dto.response.CandidateDto;
import com.diuVote.DiuVOte.dto.response.CandidateResultDto;
import com.diuVote.DiuVOte.dto.response.EventDetailsDto;
import com.diuVote.DiuVOte.dto.response.EventSummaryDto;
import com.diuVote.DiuVOte.dto.response.PublicEventSummaryDto;
import com.diuVote.DiuVOte.entity.CANDIDATE;
import com.diuVote.DiuVOte.entity.EligibilityCriteria;
import com.diuVote.DiuVOte.entity.EVENT;
import com.diuVote.DiuVOte.repository.CandidateRepository;
import com.diuVote.DiuVOte.repository.EventRepository;
import com.diuVote.DiuVOte.repository.VoteRepository;
import com.diuVote.DiuVOte.service.EventCreatorEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventCreatorEventServiceImpl implements EventCreatorEventService {

    private final EventRepository eventRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    @Override
    public EventDetailsDto createEvent(String creatorId, String creatorEmail, CreateEventRequest request) {
        EVENT event = new EVENT();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setCreatorId(creatorId);
        event.setCreatorEmail(creatorEmail);
        event.setNominationStart(Instant.ofEpochMilli(request.getNominationStartEpochMs()));
        event.setNominationEnd(Instant.ofEpochMilli(request.getNominationEndEpochMs()));
        event.setVotingStart(Instant.ofEpochMilli(request.getVotingStartEpochMs()));
        event.setVotingEnd(Instant.ofEpochMilli(request.getVotingEndEpochMs()));

        event.setCandidateEligibility(mapEligibility(request.getCandidateEligibility()));
        event.setVoterEligibility(mapEligibility(request.getVoterEligibility()));

        EVENT saved = eventRepository.save(event);
        return toDetailsDto(saved, List.of());
    }

    private EligibilityCriteria mapEligibility(EligibilityCriteriaDto dto) {
        if (dto == null) return null;

        EligibilityCriteria ec = new EligibilityCriteria();
        ec.setAllowedDepartments(dto.getAllowedDepartments());
        ec.setAllowedSessions(dto.getAllowedSessions());
        ec.setAllowedSections(dto.getAllowedSections());
        ec.setAllowedBatches(dto.getAllowedBatches());
        ec.setBatchRangeStart(dto.getBatchRangeStart());
        ec.setBatchRangeEnd(dto.getBatchRangeEnd());
        return ec;
    }

    @Override
    public List<EventSummaryDto> listMyEvents(String creatorId) {
        return eventRepository.findByCreatorId(creatorId)
                .stream()
                .map(e -> new EventSummaryDto(e.getId(), e.getName(), e.getDescription(),e.getNominationStart(),e.getNominationEnd(),e.getVotingStart(),e.getVotingEnd()))
                .collect(Collectors.toList());
    }

    @Override
    public EventDetailsDto getMyEventDetails(String creatorId, String eventId) {
        EVENT event = findEventForCreator(creatorId, eventId);
        List<CANDIDATE> candidates = candidateRepository.findByEventId(eventId);
        return buildEventDetailsWithResults(event, candidates);
    }

    @Override
    @Transactional
    public EventDetailsDto moderateCandidate(String creatorId, String eventId, String candidateId, ModerateCandidateRequest request) {
        EVENT event = findEventForCreator(creatorId, eventId);

        CANDIDATE candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        if (!event.getId().equals(candidate.getEventId())) {
            throw new IllegalArgumentException("Candidate does not belong to this event");
        }

        if (Boolean.TRUE.equals(request.getApprove())) {
            candidate.setStatus(CANDIDATE.CandidateStatus.APPROVED);
        } else {
            candidate.setStatus(CANDIDATE.CandidateStatus.REJECTED);
        }
        candidateRepository.save(candidate);

        List<CANDIDATE> candidates = candidateRepository.findByEventId(eventId);
        return buildEventDetailsWithResults(event, candidates);
    }

    @Override
    @Transactional
    public EventDetailsDto endVoting(String creatorId, String eventId) {
        EVENT event = findEventForCreator(creatorId, eventId);
        event.setVotingClosedManually(true);
        EVENT saved = eventRepository.save(event);
        List<CANDIDATE> candidates = candidateRepository.findByEventId(eventId);
        return buildEventDetailsWithResults(saved, candidates);
    }

    @Override
    @Transactional
    public EventDetailsDto releaseResults(String creatorId, String eventId) {
        EVENT event = findEventForCreator(creatorId, eventId);
        event.setResultsReleased(true);
        EVENT saved = eventRepository.save(event);
        List<CANDIDATE> candidates = candidateRepository.findByEventId(eventId);
        return buildEventDetailsWithResults(saved, candidates);
    }

    private EVENT findEventForCreator(String creatorId, String eventId) {
        EVENT event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!creatorId.equals(event.getCreatorId())) {
            throw new IllegalArgumentException("You are not the creator of this event");
        }

        return event;
    }

    @Override
    public List<PublicEventSummaryDto> listAllPublicEvents() {
        List<EVENT> events = eventRepository.findAll();

        return events.stream()
                .map(e -> new PublicEventSummaryDto(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getNominationStart(),
                        e.getNominationEnd(),
                        e.getVotingStart(),
                        e.getVotingEnd()
                ))
                .toList();
    }

    @Override
    public EventDetailsDto getPublicEventDetails(String eventId) {
        EVENT event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        List<CANDIDATE> candidates = candidateRepository.findByEventId(eventId);
        return buildEventDetailsWithResults(event, candidates);
    }

    /**
     * Build EventDetailsDto and, if voting has ended or resultsReleased is true,
     * attach aggregated vote results from the votes collection.
     */
    private EventDetailsDto buildEventDetailsWithResults(EVENT event, List<CANDIDATE> candidates) {
        EventDetailsDto dto = toDetailsDto(event, candidates);

        Instant now = Instant.now();
        Instant votingEnd = event.getVotingEnd();
        boolean votingEnded = votingEnd != null && now.isAfter(votingEnd);
        boolean resultsFlag = event.isResultsReleased();

        if (votingEnded || resultsFlag) {
            Map<String, Long> counts = voteRepository.countVotesGroupedByCandidate(event.getId());
            long totalVotes = counts.values().stream().mapToLong(Long::longValue).sum();

            List<CandidateResultDto> results = counts.entrySet().stream()
                    .map(e -> {
                        String candidateId = e.getKey();
                        long votes = e.getValue() != null ? e.getValue() : 0L;
                        double pct = totalVotes > 0 ? (votes * 100.0d / totalVotes) : 0.0d;
                        double pctRounded = Math.round(pct * 100.0d) / 100.0d;
                        return new CandidateResultDto(candidateId, votes, pctRounded);
                    })
                    .sorted(Comparator.comparingLong(CandidateResultDto::getVotes).reversed())
                    .toList();

            dto.setResultsAvailable(true);
            dto.setTotalVotes(totalVotes);
            dto.setResults(results);
        } else {
            dto.setResultsAvailable(false);
            dto.setTotalVotes(0L);
            dto.setResults(List.of());
        }

        return dto;
    }

    private EventDetailsDto toDetailsDto(EVENT event, List<CANDIDATE> candidates) {
        List<CANDIDATE> candidateList = (candidates != null) ? candidates : List.of();

        List<CandidateDto> candidateDtos = candidateList.stream()
                .map(c -> new CandidateDto(
                        c.getId(),
                        c.getName(),
                        c.getBio(),
                        c.getManifesto(),
                        c.getStatus()
                ))
                .toList();

        EventDetailsDto dto = new EventDetailsDto(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCandidateEligibility(),
                event.getVoterEligibility(),
                event.getNominationStart(),
                event.getNominationEnd(),
                event.getVotingStart(),
                event.getVotingEnd(),
                event.isNominationsClosedManually(),
                event.isVotingClosedManually(),
                event.isResultsReleased(),
                candidateDtos,
                false,
                0L,
                List.of()
        );

        return dto;
    }
}