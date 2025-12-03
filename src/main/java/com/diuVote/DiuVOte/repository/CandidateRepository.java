package com.diuVote.DiuVOte.repository;

import com.diuVote.DiuVOte.entity.CANDIDATE;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends MongoRepository<CANDIDATE, String> {
    Optional<CANDIDATE> findByEventIdAndNomineeNfcId(String eventId, String nomineeNfcId);
    List<CANDIDATE> findByEventId(String eventId);
}