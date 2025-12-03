package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.VoteRequest;
import com.diuVote.DiuVOte.dto.response.VoteResponse;

public interface VotingService {

    VoteResponse castVote(VoteRequest request);
}