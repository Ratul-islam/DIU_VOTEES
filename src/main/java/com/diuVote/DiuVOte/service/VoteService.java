package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.VoteRequest;

public interface VoteService {
    void castVote(VoteRequest request);
}