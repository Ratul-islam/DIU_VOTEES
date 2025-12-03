package com.diuVote.DiuVOte.service.impl;

import com.diuVote.DiuVOte.dto.request.VoteRequest;
import com.diuVote.DiuVOte.dto.response.VoteResponse;
import com.diuVote.DiuVOte.entity.CANDIDATE;
import com.diuVote.DiuVOte.entity.EVENT;
import com.diuVote.DiuVOte.entity.StudentProfile;
import com.diuVote.DiuVOte.entity.VOTE;
import com.diuVote.DiuVOte.repository.CandidateRepository;
import com.diuVote.DiuVOte.repository.EventRepository;
import com.diuVote.DiuVOte.repository.StudentProfileRepository;
import com.diuVote.DiuVOte.repository.VoteRepository;
import com.diuVote.DiuVOte.service.VotingService;
import com.diuVote.DiuVOte.util.EligibilityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VotingServiceImpl implements VotingService {

    private final StudentProfileRepository studentProfileRepository;
    private final EventRepository eventRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    @Override
    public VoteResponse castVote(VoteRequest request) {

        StudentProfile student = studentProfileRepository
                .findByNfcId(request.getNfcId())
                .orElseThrow(() -> new IllegalArgumentException("Student not registered with this NFC card"));

        EVENT event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Instant now = Instant.now();

        if (now.isBefore(event.getVotingStart())) {
            throw new IllegalArgumentException("Voting has not started for this event");
        }

        if (now.isAfter(event.getVotingEnd()) || event.isVotingClosedManually()) {
            throw new IllegalArgumentException("Voting is closed for this event");
        }

        CANDIDATE candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        if (!event.getId().equals(candidate.getEventId())) {
            throw new IllegalArgumentException("Candidate does not belong to this event");
        }

        if (candidate.getStatus() != CANDIDATE.CandidateStatus.APPROVED) {
            throw new IllegalArgumentException("Candidate is not approved for this event");
        }

        if (!EligibilityUtil.isEligible(event.getVoterEligibility(), student)) {
            throw new IllegalArgumentException("You are not eligible to vote in this event");
        }

        voteRepository.findByEventIdAndVoterNfcId(event.getId(), student.getNfcId())
                .ifPresent(v -> {
                    throw new IllegalArgumentException("You have already voted in this event");
                });


        VOTE vote = new VOTE();
        vote.setEventId(event.getId());
        vote.setCandidateId(candidate.getId());
        vote.setVoterNfcId(student.getNfcId());
        vote.setVoterDepartment(student.getDepartment());
        vote.setVoterSession(student.getSession());
        vote.setVoterSection(student.getSection());
        vote.setVoterBatch(student.getBatch());
        vote.setCreatedAt(Instant.now());

        VOTE saved = voteRepository.save(vote);

        return new VoteResponse(saved.getId(), saved.getEventId(), saved.getCandidateId());
    }
}