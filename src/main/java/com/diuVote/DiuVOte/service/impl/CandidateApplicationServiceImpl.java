package com.diuVote.DiuVOte.service.impl;

import com.diuVote.DiuVOte.dto.request.CandidateApplicationRequest;
import com.diuVote.DiuVOte.dto.response.CandidateApplicationResponse;
import com.diuVote.DiuVOte.entity.CANDIDATE;
import com.diuVote.DiuVOte.entity.EVENT;
import com.diuVote.DiuVOte.entity.StudentProfile;
import com.diuVote.DiuVOte.repository.CandidateRepository;
import com.diuVote.DiuVOte.repository.EventRepository;
import com.diuVote.DiuVOte.repository.StudentProfileRepository;
import com.diuVote.DiuVOte.service.CandidateApplicationService;
import com.diuVote.DiuVOte.util.EligibilityUtil;
import com.diuVote.DiuVOte.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CandidateApplicationServiceImpl implements CandidateApplicationService {

    private final StudentProfileRepository studentProfileRepository;
    private final EventRepository eventRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public CandidateApplicationResponse applyAsCandidate(CandidateApplicationRequest request) {

        StudentProfile student = studentProfileRepository
                .findByNfcId(request.getNfcId())
                .orElseThrow(() -> new IllegalArgumentException("Student not registered with this NFC card"));

        EVENT event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

                
        Instant now = Instant.now();

        if (now.isBefore(event.getNominationStart())) {
            Duration untilStart = Duration.between(now, event.getNominationStart());
            String remaining = TimeUtil.formatDuration(untilStart);
            throw new IllegalArgumentException(
                    "Nomination period has not started for this event. Starts in " + remaining + "."
            );
        }

        if (now.isAfter(event.getNominationEnd()) || event.isNominationsClosedManually()) {
            Duration sinceEnd = Duration.between(event.getNominationEnd(), now);
            String since = TimeUtil.formatDuration(sinceEnd);
            throw new IllegalArgumentException(
                    "Nomination period is closed for this event. Closed " + since + " ago."
            );
        }

        
        if (!EligibilityUtil.isEligible(event.getCandidateEligibility(), student)) {
            throw new IllegalArgumentException("You are not eligible to stand as a candidate for this event");
        }

        candidateRepository.findByEventIdAndNomineeNfcId(event.getId(), student.getNfcId())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("You have already applied as candidate for this event");
                });
 

        CANDIDATE candidate = new CANDIDATE();
        candidate.setEventId(event.getId());
        candidate.setName(request.getName());
        candidate.setBio(request.getBio());
        candidate.setManifesto(request.getManifesto());
        candidate.setNomineeNfcId(student.getNfcId());

        CANDIDATE saved = candidateRepository.save(candidate);

        return new CandidateApplicationResponse(
                saved.getId(),
                saved.getEventId(),
                saved.getName(),
                saved.getBio(),
                saved.getManifesto(),
                saved.getStatus()
        );
    }
}