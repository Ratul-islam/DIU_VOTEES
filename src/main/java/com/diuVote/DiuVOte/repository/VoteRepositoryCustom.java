package com.diuVote.DiuVOte.repository;

import java.util.Map;

public interface VoteRepositoryCustom {
    Map<String, Long> countVotesGroupedByCandidate(String eventId);
}