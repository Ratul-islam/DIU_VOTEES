package com.diuVote.DiuVOte.controller;

import com.diuVote.DiuVOte.dto.request.LoginRequest;
import com.diuVote.DiuVOte.dto.request.SignupRequest;
import com.diuVote.DiuVOte.dto.response.ApiResponse;
import com.diuVote.DiuVOte.dto.response.AuthResponse;
import com.diuVote.DiuVOte.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse authResponse = authService.signup(request);

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "User registered successfully",
                authResponse
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Login successful",
                authResponse
        );

        return ResponseEntity.ok(response);
    }
}