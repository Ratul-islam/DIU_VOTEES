package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.request.RegisterStudentRequest;
import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.StudentProfileDto;
import com.diuVote.DiuVOte.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StudentProfileDto>> registerStudent(
            @Valid @RequestBody RegisterStudentRequest request
    ) {
        StudentProfileDto profile = studentProfileService.registerOrUpdateStudent(request);

        ApiResponse<StudentProfileDto> response = new ApiResponse<>(
                true,
                "Student profile saved successfully",
                profile
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-nfc/{nfcId}")
    public ResponseEntity<ApiResponse<StudentProfileDto>> getByNfcId(@PathVariable String nfcId) {
        StudentProfileDto profile = studentProfileService.getByNfcId(nfcId);

        ApiResponse<StudentProfileDto> response = new ApiResponse<>(
                true,
                "Student profile found",
                profile
        );

        return ResponseEntity.ok(response);
    }
}