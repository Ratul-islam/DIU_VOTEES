package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.request.VoteRequest;
import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.VoteResponse;
import com.diuVote.DiuVOte.service.VotingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @PostMapping
    public ResponseEntity<ApiResponse<VoteResponse>> castVote(
            @Valid @RequestBody VoteRequest request
    ) {
        VoteResponse voteResponse = votingService.castVote(request);

        ApiResponse<VoteResponse> response = new ApiResponse<>(
                true,
                "Vote cast successfully",
                voteResponse
        );

        return ResponseEntity.ok(response);
    }
}