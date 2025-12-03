package com.diuVote.DiuVOte.service.impl;

import com.diuVote.DiuVOte.dto.request.LoginRequest;
import com.diuVote.DiuVOte.dto.request.SignupRequest;
import com.diuVote.DiuVOte.dto.response.AuthResponse;
import com.diuVote.DiuVOte.entity.EVENTCREATOR;
import com.diuVote.DiuVOte.repository.EventCreatorRepository;
import com.diuVote.DiuVOte.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements com.diuVote.DiuVOte.service.AuthService {

    private final EventCreatorRepository EventCreatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse signup(SignupRequest request) {
        if (EventCreatorRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        EVENTCREATOR user = new EVENTCREATOR();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        EventCreatorRepository.save(user);


        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}