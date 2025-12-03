package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.CandidateApplicationRequest;
import com.diuVote.DiuVOte.dto.response.CandidateApplicationResponse;

public interface CandidateApplicationService {

    CandidateApplicationResponse applyAsCandidate(CandidateApplicationRequest request);
}