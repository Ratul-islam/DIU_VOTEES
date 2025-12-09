package com.diuVote.DiuVOte.repository;

import com.diuVote.DiuVOte.entity.VOTE;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<VOTE, String>,VoteRepositoryCustom  {

    Optional<VOTE> findByEventIdAndVoterNfcId(String eventId, String voterNfcId);

    List<VOTE> findByEventId(String eventId);
}