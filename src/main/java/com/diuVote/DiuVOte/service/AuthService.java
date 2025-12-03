package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.LoginRequest;
import com.diuVote.DiuVOte.dto.request.SignupRequest;
import com.diuVote.DiuVOte.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}