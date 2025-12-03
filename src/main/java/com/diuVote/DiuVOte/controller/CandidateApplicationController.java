package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.request.CandidateApplicationRequest;
import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.CandidateApplicationResponse;
import com.diuVote.DiuVOte.service.CandidateApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final CandidateApplicationService candidateApplicationService;

    // Apply as candidate using NFC (no login)
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<CandidateApplicationResponse>> applyAsCandidate(
            @Valid @RequestBody CandidateApplicationRequest request
    ) {
        System.out.println(System.currentTimeMillis());
        CandidateApplicationResponse result = candidateApplicationService.applyAsCandidate(request);

        ApiResponse<CandidateApplicationResponse> response = new ApiResponse<>(
                true,
                "Candidate application submitted successfully",
                result
        );

        return ResponseEntity.ok(response);
    }
}